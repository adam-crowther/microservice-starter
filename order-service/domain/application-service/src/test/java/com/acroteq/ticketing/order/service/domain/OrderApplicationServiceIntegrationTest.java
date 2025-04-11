package com.acroteq.ticketing.order.service.domain;

import static com.acroteq.domain.valueobject.OrderStatus.PENDING;
import static com.acroteq.ticketing.order.service.domain.test.helper.AirlineTestDataHelper.AIRLINE;
import static com.acroteq.ticketing.order.service.domain.test.helper.AirlineTestDataHelper.AIRLINE_ID;
import static com.acroteq.ticketing.order.service.domain.test.helper.CustomerTestDataHelper.CUSTOMER;
import static com.acroteq.ticketing.order.service.domain.test.helper.CustomerTestDataHelper.createOrder;
import static com.acroteq.ticketing.order.service.domain.test.helper.FlightTestDataHelper.FLIGHT_1;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.acroteq.ticketing.order.service.domain.entity.Airline;
import com.acroteq.ticketing.order.service.domain.entity.Order;
import com.acroteq.ticketing.order.service.domain.exception.AirlineNotActiveException;
import com.acroteq.ticketing.order.service.domain.ports.input.service.OrderApplicationService;
import com.acroteq.ticketing.order.service.domain.test.config.TestConfig;
import com.acroteq.ticketing.order.service.domain.test.helper.MockInitialiser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = { TestOrderApplication.class, TestConfig.class })
class OrderApplicationServiceIntegrationTest {

  @Autowired
  private MockInitialiser mockInitialiser;
  @Autowired
  private OrderApplicationService orderApplicationService;

  @BeforeEach
  void init() {
    mockInitialiser.initialiseMocks();
  }

  @Test
  final void testCreateOrderOk() {
    // given:
    final Order order = createOrder(CUSTOMER, AIRLINE, FLIGHT_1);
    // when:
    final Order response = orderApplicationService.createOrder(order);

    // then:
    assertThat(response.getTrackingId(), is(notNullValue()));
    assertThat(response.getOrderStatus(), is(PENDING));
  }

  @Test
  final void testCreateOrderWithPassiveAirlineNok() {
    // given:
    final Airline airline = AIRLINE.toBuilder()
                                   .active(false)
                                   .build();
    final Order order = createOrder(CUSTOMER, airline, FLIGHT_1);

    // when:
    final AirlineNotActiveException exception = assertThrows(AirlineNotActiveException.class,
                                                             () -> orderApplicationService.createOrder(order));
    // then:
    assertThat(exception.getMessage(), startsWith("Airline "));
    assertThat(exception.getMessage(), containsString(AIRLINE_ID.toString()));
    assertThat(exception.getMessage(), endsWith(" is currently not active"));
  }
}
