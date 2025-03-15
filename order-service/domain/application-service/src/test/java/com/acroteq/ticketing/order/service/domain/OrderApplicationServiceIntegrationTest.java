package com.acroteq.ticketing.order.service.domain;

import static com.acroteq.domain.valueobject.OrderStatus.PENDING;
import static com.acroteq.ticketing.order.service.domain.test.helper.AirlineTestDataHelper.AIRLINE_ID;
import static com.acroteq.ticketing.order.service.domain.test.helper.CustomerTestDataHelper.createCreateOrderCommandDto;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.acroteq.domain.valueobject.AirlineId;
import com.acroteq.ticketing.order.service.domain.dto.create.CreateOrderCommandDto;
import com.acroteq.ticketing.order.service.domain.dto.create.CreateOrderResponseDto;
import com.acroteq.ticketing.order.service.domain.entity.Airline;
import com.acroteq.ticketing.order.service.domain.exception.AirlineNotActiveException;
import com.acroteq.ticketing.order.service.domain.ports.input.service.OrderApplicationService;
import com.acroteq.ticketing.order.service.domain.ports.output.repository.AirlineRepository;
import com.acroteq.ticketing.order.service.domain.test.config.TestConfig;
import com.acroteq.ticketing.order.service.domain.test.helper.AirlineTestDataHelper;
import com.acroteq.ticketing.order.service.domain.test.helper.MockInitialiser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest(classes = { TestOrderApplication.class, TestConfig.class })
class OrderApplicationServiceIntegrationTest {

  @Autowired
  private AirlineRepository airlineRepository;
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
    final CreateOrderCommandDto createOrderCommand = createCreateOrderCommandDto();
    // when:
    final CreateOrderResponseDto response = orderApplicationService.createOrder(createOrderCommand);

    // then:
    assertThat(response.getTrackingId(), is(notNullValue()));
    assertThat(response.getStatus(), is(PENDING));
    assertThat(response.getMessage(), is("Order created successfully"));
  }

  @Test
  final void testCreateOrderWithPassiveAirlineNok() {
    // given:
    final Airline airline = AirlineTestDataHelper.AIRLINE.toBuilder()
                                                         .active(false)
                                                         .build();
    when(airlineRepository.findById(AirlineId.of(AIRLINE_ID))).thenReturn(Optional.of(airline));
    final CreateOrderCommandDto createOrderCommand = createCreateOrderCommandDto();

    // when:
    final AirlineNotActiveException exception = assertThrows(AirlineNotActiveException.class,
                                                             () -> orderApplicationService.createOrder(
                                                                 createOrderCommand));
    // then:
    assertThat(exception.getMessage(), startsWith("Airline "));
    assertThat(exception.getMessage(), containsString(AIRLINE_ID.toString()));
    assertThat(exception.getMessage(), endsWith(" is currently not active"));
  }
}
