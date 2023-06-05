package com.acroteq.ticketing.order.service.container;

import static com.acroteq.ticketing.order.service.matchers.OrderServiceMatchers.matches;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import com.acroteq.ticketing.kafka.airline.avro.model.AirlineEventMessage;
import com.acroteq.ticketing.kafka.flight.approval.avro.model.AirlineApprovalApprovedResponseMessage;
import com.acroteq.ticketing.kafka.flight.approval.avro.model.AirlineApprovalApprovedResponseMessageProducer;
import com.acroteq.ticketing.kafka.flight.approval.avro.model.AirlineApprovalRequestMessage;
import com.acroteq.ticketing.kafka.payment.avro.model.PaymentPaidResponseMessage;
import com.acroteq.ticketing.kafka.payment.avro.model.PaymentPaidResponseMessageProducer;
import com.acroteq.ticketing.kafka.payment.avro.model.PaymentRequestMessage;
import com.acroteq.ticketing.order.service.client.api.OrdersApi;
import com.acroteq.ticketing.order.service.client.model.CreateOrderCommand;
import com.acroteq.ticketing.order.service.client.model.CreateOrderResponse;
import com.acroteq.ticketing.order.service.client.model.Order;
import com.acroteq.ticketing.order.service.client.model.OrderStatus;
import com.acroteq.ticketing.order.service.config.IntegrationTestConfiguration;
import com.acroteq.ticketing.order.service.data.MasterDataFixture;
import com.acroteq.ticketing.order.service.data.TestDataGenerator;
import com.acroteq.ticketing.test.container.KafkaSslContainer;
import com.acroteq.ticketing.test.extension.KafkaContainerExtension;
import com.acroteq.ticketing.test.extension.PostgreSqlContainerExtension;
import com.acroteq.ticketing.test.kafka.TestKafkaConsumer;
import lombok.extern.slf4j.Slf4j;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;
import java.util.function.Predicate;


/*
    export KAFKA_ADVERTISED_LISTENERS=LOCAL_PLAINTEXT://localhost:60347,
                                      LOCAL_SSL://localhost:60348,
                                      PLAINTEXT://kafka84572:19092,
                                      SSL://kafka84572:19093,
                                      BROKER://kafka84572:9090
 */

// This is an integration test that exercises many interfaces.  It is always going to have many imports.
@SuppressWarnings("PMD.ExcessiveImports")
@Slf4j
@ExtendWith({ PostgreSqlContainerExtension.class, KafkaContainerExtension.class })
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Import(IntegrationTestConfiguration.class)
@ActiveProfiles("test")
class OrderServiceApplicationIntegrationTest {

  private static final int POLL_INTERVAL_MILLISECONDS = 100;

  @Autowired
  private TestDataGenerator testDataGenerator;
  @Autowired
  private MasterDataFixture masterDataFixture;
  @Autowired
  private TestKafkaConsumer<PaymentRequestMessage> paymentRequestMessageConsumer;
  @Autowired
  private PaymentPaidResponseMessageProducer paymentPaidResponseMessageProducer;
  @Autowired
  private AirlineApprovalApprovedResponseMessageProducer approvedResponseMessageProducer;
  @Autowired
  private TestKafkaConsumer<AirlineApprovalRequestMessage> approvalRequestMessageConsumer;

  @Autowired
  private OrdersApi ordersApi;

  @BeforeAll
  static void setBootstrapServers(final KafkaSslContainer kafka) {
    final int mappedPlaintextBrokerPort = kafka.getMappedExposedPlaintextBrokerPort();

    final String bootstrapServers = String.format("localhost:%d", mappedPlaintextBrokerPort);
    System.setProperty("KAFKA_BOOTSTRAP_SERVERS", bootstrapServers);
  }

  @BeforeEach
  void prepareMasterData() {
    masterDataFixture.prepareMasterData();
  }

  @BeforeEach
  void subscribeToKafkaTopics() {
    paymentRequestMessageConsumer.subscribe();
    approvalRequestMessageConsumer.subscribe();
  }

  @AfterEach
  void stopConsumers() {
    paymentRequestMessageConsumer.stop();
    approvalRequestMessageConsumer.stop();
  }

  @Test
  void testCreateOrder() {
    // given:
    final long customerId = masterDataFixture.getCustomerId();
    final long flightId = masterDataFixture.getFlightId(0);

    final AirlineEventMessage airline = masterDataFixture.getAirline();
    final long airlineId = masterDataFixture.getAirlineId();

    final CreateOrderCommand createOrderCommand = testDataGenerator.getCreateOrderCommand(customerId,
                                                                                          airlineId,
                                                                                          flightId,
                                                                                          1);

    // when:
    final CreateOrderResponse createOrderResponse = ordersApi.createOrder(createOrderCommand)
                                                             .blockOptional()
                                                             .orElseGet(Assertions::fail);


    // then:
    assertThat(createOrderResponse.getStatus(), is(OrderStatus.PENDING));
    final UUID trackingId = createOrderResponse.getTrackingId();

    // when:
    final Order order = ordersApi.getOrderByTrackingId(trackingId)
                                 .blockOptional()
                                 .orElseGet(Assertions::fail);
    // then:
    assertThat(order, matches(createOrderCommand, OrderStatus.PENDING, trackingId));

    // when:
    final PaymentRequestMessage paymentRequest = paymentRequestMessageConsumer.poll()
                                                                              .blockOptional()
                                                                              .orElseGet(Assertions::fail);
    // then:
    assertThat(paymentRequest, matches(createOrderCommand, order.getId(), airline));

    // then:
    final PaymentPaidResponseMessage paymentPaidResponse = testDataGenerator.getPaymentPaidResponseMessage(
        paymentRequest);
    paymentPaidResponseMessageProducer.send(paymentPaidResponse);

    // when:
    final Order paidOrder = waitForOrderState(trackingId, OrderStatus.PAID);
    // then:
    assertThat(paidOrder, matches(createOrderCommand, OrderStatus.PAID, trackingId));

    // when:
    final AirlineApprovalRequestMessage approvalRequest = approvalRequestMessageConsumer.poll()
                                                                                        .blockOptional()
                                                                                        .orElseGet(Assertions::fail);
    // then:
    assertThat(approvalRequest, matches(createOrderCommand, paymentRequest, airline));

    // then:
    final AirlineApprovalApprovedResponseMessage approvedResponse =
        testDataGenerator.getAirlineApprovalApprovedResponseMessage(approvalRequest);
    approvedResponseMessageProducer.send(approvedResponse);

    // when:
    final Order approvedOrder = waitForOrderState(trackingId, OrderStatus.APPROVED);
    // then:
    assertThat(approvedOrder, matches(createOrderCommand, OrderStatus.APPROVED, trackingId));
  }

  private Order waitForOrderState(final UUID trackingId, final OrderStatus expectedState) {
    return Awaitility.await()
                     .pollInterval(POLL_INTERVAL_MILLISECONDS, MILLISECONDS)
                     .until(() -> ordersApi.getOrderByTrackingId(trackingId)
                                           .blockOptional()
                                           .orElseGet(Assertions::fail), hasExpectedState(expectedState));
  }

  private Predicate<Order> hasExpectedState(final OrderStatus expectedState) {
    return order -> hasExpectedState(order, expectedState);
  }

  private boolean hasExpectedState(final Order order, final OrderStatus expectedState) {
    final OrderStatus orderStatus = order.getStatus();
    return orderStatus == expectedState;
  }
}
