package com.acroteq.ticketing.order.service.container;

import static com.acroteq.ticketing.order.service.client.model.OrderStatus.PENDING;
import static com.github.npathai.hamcrestopt.OptionalMatchers.isPresentAndIs;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import com.acroteq.ticketing.kafka.airline.avro.model.AirlineEventMessage;
import com.acroteq.ticketing.kafka.airline.avro.model.Flight;
import com.acroteq.ticketing.kafka.customer.avro.model.CustomerEventMessage;
import com.acroteq.ticketing.order.service.client.api.OrdersApi;
import com.acroteq.ticketing.order.service.client.model.CreateOrderCommand;
import com.acroteq.ticketing.order.service.client.model.CreateOrderResponse;
import com.acroteq.ticketing.order.service.config.IntegrationTestConfiguration;
import com.acroteq.ticketing.order.service.data.AirlineMasterDataGenerator;
import com.acroteq.ticketing.order.service.data.CreateOrderCommandGenerator;
import com.acroteq.ticketing.order.service.data.CustomerMasterDataGenerator;
import com.acroteq.ticketing.order.service.data.MasterDataUploader;
import com.acroteq.ticketing.order.service.extension.KafkaContainerExtension;
import com.acroteq.ticketing.order.service.extension.PostgreSqlContainerExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;

import java.util.Optional;

@ExtendWith({ PostgreSqlContainerExtension.class, KafkaContainerExtension.class })
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Import(IntegrationTestConfiguration.class)
@ActiveProfiles("test")
class OrderServiceApplicationIntegrationTest {

  @Autowired
  private AirlineMasterDataGenerator airlineGenerator;
  @Autowired
  private CustomerMasterDataGenerator customerGenerator;
  @Autowired
  private CreateOrderCommandGenerator orderCommandGenerator;
  @Autowired
  private MasterDataUploader<AirlineEventMessage> airlineMasterDataUploader;
  @Autowired
  private MasterDataUploader<CustomerEventMessage> customerMasterDataUploader;

  @Autowired
  private OrdersApi ordersApi;

  private AirlineEventMessage airline;
  private CustomerEventMessage customer;

  @BeforeEach
  void prepareMasterData() {
    airline = airlineGenerator.getAirlineEventMessage();
    airlineMasterDataUploader.upload(airline.getId(), airline);

    customer = customerGenerator.getCustomerEventMessage();
    customerMasterDataUploader.upload(customer.getId(), customer);
  }

  @Test
  void testCreateOrder() {
    // given:
    final long customerId = customer.getId();
    final long airlineId = airline.getId();
    final Flight flight = airline.getFlights()
                                 .get(0);
    final long flightId = flight.getId();

    final CreateOrderCommand createOrderCommand = orderCommandGenerator.getCreateOrderCommand(customerId,
                                                                                              airlineId,
                                                                                              flightId,
                                                                                              1);
    // when:
    final Mono<CreateOrderResponse> order = ordersApi.createOrder(createOrderCommand);
    // then:
    final Optional<CreateOrderResponse> createOrderResponse = order.blockOptional();
    assertThat(createOrderResponse.map(CreateOrderResponse::getStatus), isPresentAndIs(PENDING));
  }
}
