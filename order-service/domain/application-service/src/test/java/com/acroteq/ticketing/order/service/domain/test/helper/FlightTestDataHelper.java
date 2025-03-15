package com.acroteq.ticketing.order.service.domain.test.helper;

import static org.mockito.Mockito.lenient;

import com.acroteq.domain.valueobject.CashValue;
import com.acroteq.domain.valueobject.CurrencyId;
import com.acroteq.domain.valueobject.EventId;
import com.acroteq.domain.valueobject.FlightId;
import com.acroteq.ticketing.order.service.domain.entity.Flight;
import com.acroteq.ticketing.order.service.domain.ports.output.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.TestComponent;

import java.math.BigDecimal;
import java.util.Optional;

@RequiredArgsConstructor
@TestComponent
public class FlightTestDataHelper {

  public static final String CURRENCY_ID = "CHF";
  public static final Long FLIGHT_1_ID = IdTestDataHelper.nextId();
  public static final String FLIGHT_1_NAME = "flight-1";
  public static final BigDecimal FLIGHT_1_PRICE = new BigDecimal("10.00");
  public static final Flight FLIGHT_1 = Flight.builder()
                                              .id(FlightId.of(FLIGHT_1_ID))
                                              .version(0L)
                                              .eventId(EventId.builder()
                                                              .partition(0)
                                                              .offset(0L)
                                                              .build())
                                              .flightNumber(FLIGHT_1_NAME)
                                              .price(CashValue.builder()
                                                              .amount(FLIGHT_1_PRICE)
                                                              .currencyId(CurrencyId.of(CURRENCY_ID))
                                                              .build())
                                              .build();


  public static final Long FLIGHT_2_ID = IdTestDataHelper.nextId();
  public static final String FLIGHT_2_NAME = "flight-2";
  public static final BigDecimal FLIGHT_2_PRICE = new BigDecimal("20.00");
  public static final Flight FLIGHT_2 = Flight.builder()
                                              .id(FlightId.of(FLIGHT_2_ID))
                                              .version(0L)
                                              .eventId(EventId.builder()
                                                              .partition(0)
                                                              .offset(1L)
                                                              .build())
                                              .flightNumber(FLIGHT_2_NAME)
                                              .price(CashValue.builder()
                                                              .amount(FLIGHT_2_PRICE)
                                                              .currencyId(CurrencyId.of(CURRENCY_ID))
                                                              .build())
                                              .build();

  public static final Long FLIGHT_3_ID = IdTestDataHelper.nextId();
  public static final String FLIGHT_3_NAME = "flight-3";
  public static final BigDecimal FLIGHT_3_PRICE = new BigDecimal("30.00");
  public static final Flight FLIGHT_3 = Flight.builder()
                                              .id(FlightId.of(FLIGHT_3_ID))
                                              .version(0L)
                                              .eventId(EventId.builder()
                                                              .partition(0)
                                                              .offset(2L)
                                                              .build())
                                              .flightNumber(FLIGHT_3_NAME)
                                              .price(CashValue.builder()
                                                              .amount(FLIGHT_3_PRICE)
                                                              .currencyId(CurrencyId.of(CURRENCY_ID))
                                                              .build())
                                              .build();

  private final FlightRepository flightRepository;

  public void initialiseMocks() {
    lenient().when(flightRepository.findById(FlightId.of(FLIGHT_1_ID)))
             .thenReturn(Optional.of(FLIGHT_1));
    lenient().when(flightRepository.findById(FlightId.of(FLIGHT_2_ID)))
             .thenReturn(Optional.of(FLIGHT_2));
    lenient().when(flightRepository.findById(FlightId.of(FLIGHT_3_ID)))
             .thenReturn(Optional.of(FLIGHT_3));
  }
}
