package com.acroteq.ticketing.order.service.domain.test.helper;

import static org.mockito.Mockito.lenient;

import com.acroteq.ticketing.domain.valueobject.CashValue;
import com.acroteq.ticketing.domain.valueobject.CurrencyId;
import com.acroteq.ticketing.domain.valueobject.FlightId;
import com.acroteq.ticketing.order.service.domain.entity.Flight;
import com.acroteq.ticketing.order.service.domain.ports.output.repository.AirlineFlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.TestComponent;

import java.math.BigDecimal;
import java.util.Optional;

@RequiredArgsConstructor
@TestComponent
public class FlightTestDataHelper {

  public static final Long CURRENCY_ID = IdTestDataHelper.nextId();
  public static final Long FLIGHT_1_ID = IdTestDataHelper.nextId();
  public static final String FLIGHT_1_NAME = "FLIGHT 1";
  public static final BigDecimal FLIGHT_1_PRICE = new BigDecimal("10.00");
  public static final Flight FLIGHT_1 = Flight.builder()
                                              .id(FlightId.of(FLIGHT_1_ID))
                                              .flightNumber(FLIGHT_1_NAME)
                                              .price(CashValue.builder()
                                                              .amount(FLIGHT_1_PRICE)
                                                              .currencyId(CurrencyId.of(CURRENCY_ID))
                                                              .build())
                                              .build();


  public static final Long FLIGHT_2_ID = IdTestDataHelper.nextId();
  public static final String FLIGHT_2_NAME = "FLIGHT 2";
  public static final BigDecimal FLIGHT_2_PRICE = new BigDecimal("20.00");
  public static final Flight FLIGHT_2 = Flight.builder()
                                              .id(FlightId.of(FLIGHT_2_ID))
                                              .flightNumber(FLIGHT_2_NAME)
                                              .price(CashValue.builder()
                                                              .amount(FLIGHT_2_PRICE)
                                                              .currencyId(CurrencyId.of(CURRENCY_ID))
                                                              .build())
                                              .build();

  public static final Long FLIGHT_3_ID = IdTestDataHelper.nextId();
  public static final String FLIGHT_3_NAME = "FLIGHT 3";
  public static final BigDecimal FLIGHT_3_PRICE = new BigDecimal("30.00");
  public static final Flight FLIGHT_3 = Flight.builder()
                                              .id(FlightId.of(FLIGHT_3_ID))
                                              .flightNumber(FLIGHT_3_NAME)
                                              .price(CashValue.builder()
                                                              .amount(FLIGHT_3_PRICE)
                                                              .currencyId(CurrencyId.of(CURRENCY_ID))
                                                              .build())
                                              .build();

  private final AirlineFlightRepository airlineFlightRepository;

  public void initialiseMocks() {
    lenient().when(airlineFlightRepository.loadFlight(FlightId.of(FLIGHT_1_ID)))
             .thenReturn(Optional.of(FLIGHT_1));
    lenient().when(airlineFlightRepository.loadFlight(FlightId.of(FLIGHT_2_ID)))
             .thenReturn(Optional.of(FLIGHT_2));
    lenient().when(airlineFlightRepository.loadFlight(FlightId.of(FLIGHT_3_ID)))
             .thenReturn(Optional.of(FLIGHT_3));
  }
}
