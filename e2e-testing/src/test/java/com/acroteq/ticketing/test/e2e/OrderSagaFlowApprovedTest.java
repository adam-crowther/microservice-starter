package com.acroteq.ticketing.test.e2e;

import static com.acroteq.ticketing.order.service.client.model.OrderStatus.APPROVED;
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
import com.acroteq.ticketing.airline.service.client.model.Flight;
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
import com.acroteq.ticketing.test.e2e.data.TestDataGenerator;
import com.acroteq.ticketing.test.e2e.extension.TestDockerContainers;
import com.acroteq.ticketing.test.e2e.extension.TestcontainersLifecycleExtension;
import lombok.extern.slf4j.Slf4j;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@SuppressWarnings("PMD.ExcessiveImports")
@ExtendWith({ TestcontainersLifecycleExtension.class })
class OrderSagaFlowApprovedTest {

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
  void testOrderApprovedFlow() {
    // Create the customer through the CustomerMdm REST API
    // given:
    final CreateCustomerCommand createCustomerCommand = testDataGenerator.getCreateCustomerCommand();
    // when:
    final CreateCustomerResponse customerResponse = createClient(createCustomerCommand);
    // then:
    waitForCustomerReplication();

    // Get the customer back from the CustomerMdm REST API
    // when:
    final Long customerId = customerResponse.getId();
    final Customer customer = getCustomer(customerId);
    // then:
    assertThat(customer, CustomerMatcher.matches(createCustomerCommand));

    // Create the airline through the AirlineMdm REST API
    // given:
    final CreateAirlineCommand createAirlineCommand = testDataGenerator.getCreateAirlineCommand();
    // when:
    final CreateAirlineResponse airlineResponse = createAirline(createAirlineCommand);
    // then:
    waitForAirlineReplication();

    // Get the airline back from the AirlineMdm REST API
    // when:
    final Long airlineId = airlineResponse.getId();
    final Airline airline = getAirline(airlineId);
    // then:
    assertThat(airline, AirlineMatcher.matches(createAirlineCommand));

    // Create an order through the OrderService REST API
    // given:
    final Long flightId = getFlightId(airline);
    final CreateOrderCommand createOrderCommand = testDataGenerator.getCreateOrderCommand(customerId,
                                                                                          airlineId,
                                                                                          flightId,
                                                                                          1);
    // when:
    final UUID trackingId = createOrder(createOrderCommand);

    // then:
    // The Saga Flow should process the order all the way through to state Approved
    waitForOrderState(trackingId, APPROVED);
    final Order order = getOrderByTrackingId(trackingId);
    assertThat(order, OrderMatcher.matches(createOrderCommand, APPROVED, trackingId));
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

  private void waitForCustomerReplication() {
    databaseChecker.inCustomerMdmWaitForCustomerCount(1);
    databaseChecker.inOrderServiceWaitForCustomerCount(1);
    databaseChecker.inPaymentServiceWaitForCustomerCount(1);
  }

  private CreateAirlineResponse createAirline(final CreateAirlineCommand createAirlineCommand) {
    return airlinesApi.createAirline(createAirlineCommand)
                      .blockOptional()
                      .orElseThrow();
  }

  private void waitForAirlineReplication() {
    databaseChecker.inAirlineMdmWaitForAirlineCount(1);
    databaseChecker.inAirlineApprovalWaitForAirlineCount(1);
    databaseChecker.inOrderServiceWaitForAirlineCount(1);
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

    databaseChecker.inOrderServiceWaitForOrdersCount(1);
    databaseChecker.inPaymentServiceWaitForPaymentsCount(1);

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

  private Long getFlightId(final Airline airline) {
    return Optional.of(airline)
                   .map(Airline::getFlights)
                   .stream()
                   .flatMap(List::stream)
                   .map(Flight::getId)
                   .findFirst()
                   .orElseThrow();
  }

  private OrderStatus getOrderStatus(final UUID trackingId) {
    final Order order = getOrderByTrackingId(trackingId);
    return order.getStatus();
  }
}
