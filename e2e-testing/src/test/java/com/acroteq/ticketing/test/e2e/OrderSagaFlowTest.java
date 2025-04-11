package com.acroteq.ticketing.test.e2e;

import static com.acroteq.ticketing.order.service.client.model.OrderStatus.APPROVED;
import static com.acroteq.ticketing.order.service.client.model.OrderStatus.CANCELLED;
import static com.acroteq.ticketing.order.service.client.model.OrderStatus.PENDING;
import static com.acroteq.ticketing.test.e2e.api.AuthenticationHelper.authenticate;
import static com.acroteq.ticketing.test.e2e.api.ClientApiFactory.createAirlinesApi;
import static com.acroteq.ticketing.test.e2e.api.ClientApiFactory.createCustomersApi;
import static com.acroteq.ticketing.test.e2e.api.ClientApiFactory.createOrdersApi;
import static com.acroteq.ticketing.test.e2e.api.DatabaseCheckerFactory.createQueryExecutor;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.acroteq.infrastructure.data.access.counter.AirlineApprovalServiceDatabaseChecker;
import com.acroteq.infrastructure.data.access.counter.AirlineMdmDatabaseChecker;
import com.acroteq.infrastructure.data.access.counter.CustomerMdmDatabaseChecker;
import com.acroteq.infrastructure.data.access.counter.JdbcQueryExecutor;
import com.acroteq.infrastructure.data.access.counter.OrderServiceDatabaseChecker;
import com.acroteq.infrastructure.data.access.counter.PaymentServiceDatabaseChecker;
import com.acroteq.test.extension.KafkaContainerExtension;
import com.acroteq.ticketing.airline.service.client.api.AirlinesApi;
import com.acroteq.ticketing.airline.service.client.matcher.AirlineMatcher;
import com.acroteq.ticketing.airline.service.client.model.Airline;
import com.acroteq.ticketing.airline.service.client.model.CreateAirline;
import com.acroteq.ticketing.airline.service.client.model.CreateFlight;
import com.acroteq.ticketing.customer.service.client.api.CustomersApi;
import com.acroteq.ticketing.customer.service.client.matcher.CustomerMatcher;
import com.acroteq.ticketing.customer.service.client.model.CreateCustomer;
import com.acroteq.ticketing.customer.service.client.model.Customer;
import com.acroteq.ticketing.order.service.client.api.OrdersApi;
import com.acroteq.ticketing.order.service.client.matchers.OrderMatcher;
import com.acroteq.ticketing.order.service.client.model.CreateOrder;
import com.acroteq.ticketing.order.service.client.model.CreateOrderResponse;
import com.acroteq.ticketing.order.service.client.model.Order;
import com.acroteq.ticketing.order.service.client.model.OrderStatus;
import com.acroteq.ticketing.test.e2e.data.MasterData;
import com.acroteq.ticketing.test.e2e.data.TestDataGenerator;
import com.acroteq.ticketing.test.e2e.extension.TestContainersExtension;
import com.acroteq.ticketing.test.e2e.extension.TestDockerContainers;
import lombok.extern.slf4j.Slf4j;
import org.awaitility.Awaitility;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

@Slf4j
@SuppressWarnings({ "PMD.ExcessiveImports", "PMD.CouplingBetweenObjects" })
@ExtendWith({ KafkaContainerExtension.class, TestContainersExtension.class })
class OrderSagaFlowTest {

  private static CustomersApi customersApi;
  private static AirlinesApi airlinesApi;
  private static OrdersApi ordersApi;

  private static AirlineMdmDatabaseChecker airlineMdmDbChecker;
  private static CustomerMdmDatabaseChecker customerMdmDbChecker;
  private static OrderServiceDatabaseChecker orderServiceDbChecker;
  private static PaymentServiceDatabaseChecker paymentServiceDbChecker;
  private static AirlineApprovalServiceDatabaseChecker airlineApprovalServiceDbChecker;

  private static TestDataGenerator testDataGenerator;

  @SuppressWarnings({ "PMD.CloseResource" })
  @BeforeAll
  static void startUp(final TestDockerContainers containers) {
    testDataGenerator = new TestDataGenerator();

    final String bearerToken = authenticate(containers);
    customersApi = createCustomersApi(containers, bearerToken);
    airlinesApi = createAirlinesApi(containers, bearerToken);
    ordersApi = createOrdersApi(containers, bearerToken);

    final JdbcQueryExecutor queryExecutor = createQueryExecutor(containers);
    airlineMdmDbChecker = new AirlineMdmDatabaseChecker(queryExecutor);
    customerMdmDbChecker = new CustomerMdmDatabaseChecker(queryExecutor);
    orderServiceDbChecker = new OrderServiceDatabaseChecker(queryExecutor);
    paymentServiceDbChecker = new PaymentServiceDatabaseChecker(queryExecutor);
    airlineApprovalServiceDbChecker = new AirlineApprovalServiceDatabaseChecker(queryExecutor);
  }

  @AfterAll
  static void shutdown() {
    airlineMdmDbChecker.close();
    customerMdmDbChecker.close();
    orderServiceDbChecker.close();
    paymentServiceDbChecker.close();
    airlineApprovalServiceDbChecker.close();
  }

  @Test
  void testOrderApproved() {
    // Customer more than enough credit.
    final MasterData masterData = createMasterData(2000.0, 200.0);

    // Create an order through the OrderService REST API
    // given:
    final CreateOrder createOrder = testDataGenerator.getCreateOrder(masterData, 1);
    final int orderCount = orderServiceDbChecker.getOrderCount();
    // when:
    final UUID trackingId = createOrder(createOrder);
    // then:
    orderServiceDbChecker.waitForOrdersCount(orderCount + 1);
    paymentServiceDbChecker.waitForPaymentsCount(orderCount + 1);

    // then:
    // The Saga Flow should process the order all the way through to state Approved
    waitForOrderState(trackingId, APPROVED);
    final Order order = getOrderByTrackingId(trackingId);
    assertThat(order, OrderMatcher.matches(createOrder, APPROVED, trackingId));
  }

  @Test
  void testPaymentRejected() {
    // Customer only has enough credit for 2 flights at 250 each.
    final MasterData masterData = createMasterData(510.0, 250.0);

    // Create a first order through the OrderService REST API.  Payment should be rejected because the
    // customer does not
    // have enough credit.
    // given:
    final CreateOrder rejectedOrderCommand = testDataGenerator.getCreateOrder(masterData, 3);
    final int orderCount = orderServiceDbChecker.getOrderCount();
    // when:
    final UUID rejectedTrackingId = createOrder(rejectedOrderCommand);
    // then:
    orderServiceDbChecker.waitForOrdersCount(orderCount + 1);
    paymentServiceDbChecker.waitForPaymentsCount(orderCount + 1);

    // then:
    // The Payment service should reject the payment and the Saga Flow handler should cancel the rejectedOrder
    waitForOrderState(rejectedTrackingId, CANCELLED);
    final Order rejectedOrder = getOrderByTrackingId(rejectedTrackingId);
    assertThat(rejectedOrder, OrderMatcher.matches(rejectedOrderCommand, CANCELLED, rejectedTrackingId));

    // Try again with fewer flights.  Payment should be accepted.
    // given:
    final CreateOrder acceptedOrderCommand = testDataGenerator.getCreateOrder(masterData, 2);
    // when:
    final UUID acceptedTrackingId = createOrder(acceptedOrderCommand);
    // then:
    orderServiceDbChecker.waitForOrdersCount(orderCount + 2);
    paymentServiceDbChecker.waitForPaymentsCount(orderCount + 2);

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
    final CreateCustomer createCustomer = testDataGenerator.getCreateCustomer(creditLimit);
    // when:
    final com.acroteq.ticketing.customer.service.client.model.AuditedEntityResponse customerResponse = //
        createCustomer(createCustomer);
    // then:
    waitForCustomerReplication(customerResponse.getId());

    // Get the customer back from the CustomerMdm REST API
    // when:
    final Long customerId = customerResponse.getId();
    final Customer customer = getCustomer(customerId);
    // then:
    assertThat(customer, CustomerMatcher.matches(createCustomer));

    // Create the airline through the AirlineMdm REST API
    // given:
    final CreateAirline createAirline = testDataGenerator.getCreateAirline();
    // when:
    final com.acroteq.ticketing.airline.service.client.model.AuditedEntityResponse airlineResponse = //
        createAirline(createAirline);
    // then:
    waitForAirlineReplication(airlineResponse.getId());

    // Create the flight through the AirlineMdm REST API
    // given:
    final CreateFlight createFlight = testDataGenerator.getCreateFlight(flightPrice);
    // when:
    final com.acroteq.ticketing.airline.service.client.model.EntityResponse flightResponse = //
        createFlight(createAirline.getCode(), createFlight);
    // then:
    waitForFlightReplication(flightResponse.getId());

    // Get the airline back from the AirlineMdm REST API
    // when:
    final String airlineCode = createAirline.getCode();
    final Airline airline = getAirline(airlineCode);
    // then:
    assertThat(airline, AirlineMatcher.matches(createAirline));

    return MasterData.builder()
                     .customer(customer)
                     .airline(airline)
                     .build();
  }

  private com.acroteq.ticketing.customer.service.client.model.AuditedEntityResponse createCustomer(
      final CreateCustomer createCustomer) {
    return customersApi.createCustomer(createCustomer)
                       .blockOptional()
                       .orElseThrow();
  }

  private Customer getCustomer(final Long customerId) {
    return customersApi.getCustomerById(customerId)
                       .blockOptional()
                       .orElseThrow();
  }

  private void waitForCustomerReplication(final Long customerId) {
    customerMdmDbChecker.waitForCustomer(customerId);
    orderServiceDbChecker.waitForCustomer(customerId);
    paymentServiceDbChecker.waitForCustomer(customerId);
  }

  private com.acroteq.ticketing.airline.service.client.model.AuditedEntityResponse createAirline(
      final CreateAirline createAirline) {
    return airlinesApi.createAirline(createAirline)
                      .blockOptional()
                      .orElseThrow();
  }

  private void waitForAirlineReplication(final Long airlineId) {
    airlineMdmDbChecker.waitForAirline(airlineId);
    airlineApprovalServiceDbChecker.waitForAirline(airlineId);
    orderServiceDbChecker.waitForAirline(airlineId);
  }

  private com.acroteq.ticketing.airline.service.client.model.EntityResponse createFlight(
      final String airlineCode, final CreateFlight createFlight) {
    return airlinesApi.createAirlineFlight(airlineCode, createFlight)
                      .blockOptional()
                      .orElseThrow();
  }

  private void waitForFlightReplication(final Long flightId) {
    airlineMdmDbChecker.waitForFlight(flightId);
    airlineApprovalServiceDbChecker.waitForFlight(flightId);
    orderServiceDbChecker.waitForFlight(flightId);
  }

  private Airline getAirline(final String code) {
    return airlinesApi.getAirlineByCode(code)
                      .blockOptional()
                      .orElseThrow();
  }

  private UUID createOrder(final CreateOrder createOrder) {
    final CreateOrderResponse orderResponse = ordersApi.createOrder(createOrder)
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
