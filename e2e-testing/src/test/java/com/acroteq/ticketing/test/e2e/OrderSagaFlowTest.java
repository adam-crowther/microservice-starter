package com.acroteq.ticketing.test.e2e;

import static com.acroteq.ticketing.order.service.client.model.OrderStatus.APPROVED;
import static com.acroteq.ticketing.order.service.client.model.OrderStatus.CANCELLED;
import static com.acroteq.ticketing.order.service.client.model.OrderStatus.PENDING;
import static com.acroteq.ticketing.test.e2e.api.AuthenticationHelper.authenticate;
import static com.acroteq.ticketing.test.e2e.api.ClientApiFactory.createAirlinesApi;
import static com.acroteq.ticketing.test.e2e.api.ClientApiFactory.createCustomersApi;
import static com.acroteq.ticketing.test.e2e.api.ClientApiFactory.createOrdersApi;
import static com.acroteq.ticketing.test.e2e.api.DatabaseCheckerFactory.createDatabaseChecker;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.acroteq.ticketing.airline.service.client.api.AirlinesApi;
import com.acroteq.ticketing.airline.service.client.matcher.AirlineMatcher;
import com.acroteq.ticketing.airline.service.client.model.Airline;
import com.acroteq.ticketing.airline.service.client.model.CreateAirlineCommand;
import com.acroteq.ticketing.airline.service.client.model.CreateAirlineResponse;
import com.acroteq.ticketing.customer.service.client.api.CustomersApi;
import com.acroteq.ticketing.customer.service.client.matcher.CustomerMatcher;
import com.acroteq.ticketing.customer.service.client.model.CreateCustomerCommand;
import com.acroteq.ticketing.customer.service.client.model.CreateCustomerResponse;
import com.acroteq.ticketing.customer.service.client.model.Customer;
import com.acroteq.ticketing.infrastructure.data.access.counter.JdbcDatabaseChecker;
import com.acroteq.ticketing.order.service.client.api.OrdersApi;
import com.acroteq.ticketing.order.service.client.matchers.OrderMatcher;
import com.acroteq.ticketing.order.service.client.model.CreateOrderCommand;
import com.acroteq.ticketing.order.service.client.model.CreateOrderResponse;
import com.acroteq.ticketing.order.service.client.model.Order;
import com.acroteq.ticketing.order.service.client.model.OrderStatus;
import com.acroteq.ticketing.test.e2e.data.MasterData;
import com.acroteq.ticketing.test.e2e.data.TestDataGenerator;
import com.acroteq.ticketing.test.e2e.extension.TestDockerContainers;
import com.acroteq.ticketing.test.e2e.extension.TestcontainersLifecycleExtension;
import lombok.extern.slf4j.Slf4j;
import org.awaitility.Awaitility;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

@Slf4j
@SuppressWarnings("PMD.ExcessiveImports")
@ExtendWith({ TestcontainersLifecycleExtension.class })
class OrderSagaFlowTest {

  private static CustomersApi customersApi;
  private static AirlinesApi airlinesApi;
  private static OrdersApi ordersApi;

  private static JdbcDatabaseChecker databaseChecker;

  private final TestDataGenerator testDataGenerator = new TestDataGenerator();

  @BeforeAll
  static void startUp(final TestDockerContainers containers) {

    final String bearerToken = authenticate(containers);
    customersApi = createCustomersApi(containers, bearerToken);
    airlinesApi = createAirlinesApi(containers, bearerToken);
    ordersApi = createOrdersApi(containers, bearerToken);
    databaseChecker = createDatabaseChecker(containers);
  }

  @AfterAll
  static void shutdown() {
    databaseChecker.close();
  }

  @Test
  void testOrderApproved() {
    // Customer more than enough credit.
    final MasterData masterData = createMasterData(2000.0, 200.0);

    // Create an order through the OrderService REST API
    // given:
    final CreateOrderCommand createOrderCommand = testDataGenerator.getCreateOrderCommand(masterData, 1);
    final int orderCount = databaseChecker.inOrderServiceGetOrderCount();
    // when:
    final UUID trackingId = createOrder(createOrderCommand);
    // then:
    databaseChecker.inOrderServiceWaitForOrdersCount(orderCount + 1);
    databaseChecker.inPaymentServiceWaitForPaymentsCount(orderCount + 1);

    // then:
    // The Saga Flow should process the order all the way through to state Approved
    waitForOrderState(trackingId, APPROVED);
    final Order order = getOrderByTrackingId(trackingId);
    assertThat(order, OrderMatcher.matches(createOrderCommand, APPROVED, trackingId));
  }

  @Test
  void testPaymentRejected() {
    // Customer only has enough credit for 2 flights at 250 each.
    final MasterData masterData = createMasterData(510.0, 250.0);

    // Create a first order through the OrderService REST API.  Payment should be rejected because the
    // customer does not
    // have enough credit.
    // given:
    final CreateOrderCommand rejectedOrderCommand = testDataGenerator.getCreateOrderCommand(masterData, 3);
    final int orderCount = databaseChecker.inOrderServiceGetOrderCount();
    // when:
    final UUID rejectedTrackingId = createOrder(rejectedOrderCommand);
    // then:
    databaseChecker.inOrderServiceWaitForOrdersCount(orderCount + 1);
    databaseChecker.inPaymentServiceWaitForPaymentsCount(orderCount + 1);

    // then:
    // The Payment service should reject the payment and the Saga Flow handler should cancel the rejectedOrder
    waitForOrderState(rejectedTrackingId, CANCELLED);
    final Order rejectedOrder = getOrderByTrackingId(rejectedTrackingId);
    assertThat(rejectedOrder, OrderMatcher.matches(rejectedOrderCommand, CANCELLED, rejectedTrackingId));

    // Try again with fewer flights.  Payment should be accepted.
    // given:
    final CreateOrderCommand acceptedOrderCommand = testDataGenerator.getCreateOrderCommand(masterData, 2);
    // when:
    final UUID acceptedTrackingId = createOrder(acceptedOrderCommand);
    // then:
    databaseChecker.inOrderServiceWaitForOrdersCount(orderCount + 2);
    databaseChecker.inPaymentServiceWaitForPaymentsCount(orderCount + 2);

    // then:
    // The Payment service should reject the payment and the Saga Flow handler should cancel the rejectedOrder
    waitForOrderState(acceptedTrackingId, APPROVED);
    final Order acceptedOrder = getOrderByTrackingId(acceptedTrackingId);
    assertThat(acceptedOrder, OrderMatcher.matches(acceptedOrderCommand, APPROVED, acceptedTrackingId));
  }

  @NotNull
  private MasterData createMasterData(final Double creditLimit, final Double flightPrice) {
    // given:
    // Create the customer through the CustomerMdm REST API.
    final CreateCustomerCommand createCustomerCommand = testDataGenerator.getCreateCustomerCommand(creditLimit);
    // when:
    final CreateCustomerResponse customerResponse = createClient(createCustomerCommand);
    // then:
    waitForCustomerReplication(customerResponse.getId());

    // Get the customer back from the CustomerMdm REST API
    // when:
    final Long customerId = customerResponse.getId();
    final Customer customer = getCustomer(customerId);
    // then:
    assertThat(customer, CustomerMatcher.matches(createCustomerCommand));

    // Create the airline through the AirlineMdm REST API
    // given:
    final CreateAirlineCommand createAirlineCommand = testDataGenerator.getCreateAirlineCommand(flightPrice);
    // when:
    final CreateAirlineResponse airlineResponse = createAirline(createAirlineCommand);
    // then:
    waitForAirlineReplication(airlineResponse.getId());

    // Get the airline back from the AirlineMdm REST API
    // when:
    final Long airlineId = airlineResponse.getId();
    final Airline airline = getAirline(airlineId);
    // then:
    assertThat(airline, AirlineMatcher.matches(createAirlineCommand));

    return MasterData.builder()
                     .customer(customer)
                     .airline(airline)
                     .build();
  }

  private CreateCustomerResponse createClient(final CreateCustomerCommand createCustomerCommand) {
    return customersApi.createCustomer(createCustomerCommand)
                       .blockOptional()
                       .orElseThrow();
  }

  private Customer getCustomer(final Long customerId) {
    return customersApi.getCustomerById(customerId)
                       .blockOptional()
                       .orElseThrow();
  }

  private void waitForCustomerReplication(final Long customerId) {
    databaseChecker.inCustomerMdmWaitForCustomer(customerId);
    databaseChecker.inOrderServiceWaitForCustomer(customerId);
    databaseChecker.inPaymentServiceWaitForCustomer(customerId);
  }

  private CreateAirlineResponse createAirline(final CreateAirlineCommand createAirlineCommand) {
    return airlinesApi.createAirline(createAirlineCommand)
                      .blockOptional()
                      .orElseThrow();
  }

  private void waitForAirlineReplication(final Long airlineId) {
    databaseChecker.inAirlineMdmWaitForAirline(airlineId);
    databaseChecker.inAirlineApprovalWaitForAirline(airlineId);
    databaseChecker.inOrderServiceWaitForAirline(airlineId);
  }

  private Airline getAirline(final Long airlineId) {
    return airlinesApi.getAirlineById(airlineId)
                      .blockOptional()
                      .orElseThrow();
  }

  private UUID createOrder(final CreateOrderCommand createOrderCommand) {
    final CreateOrderResponse orderResponse = ordersApi.createOrder(createOrderCommand)
                                                       .blockOptional()
                                                       .orElseThrow();
    final UUID trackingId = orderResponse.getTrackingId();
    assertThat(orderResponse.getStatus(), is(PENDING));
    return trackingId;
  }

  private void waitForOrderState(final UUID trackingId, final OrderStatus orderStatus) {
    Awaitility.await()
              .until(() -> getOrderStatus(trackingId) == orderStatus);
  }

  private Order getOrderByTrackingId(final UUID trackingId) {
    return ordersApi.getOrderByTrackingId(trackingId)
                    .blockOptional()
                    .orElseThrow();
  }

  private OrderStatus getOrderStatus(final UUID trackingId) {
    final Order order = getOrderByTrackingId(trackingId);
    return order.getStatus();
  }
}
