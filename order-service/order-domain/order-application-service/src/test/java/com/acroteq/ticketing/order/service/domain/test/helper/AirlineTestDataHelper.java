package com.acroteq.ticketing.order.service.domain.test.helper;

import static org.mockito.Mockito.lenient;

import com.acroteq.ticketing.domain.valueobject.AirlineId;
import com.acroteq.ticketing.order.service.domain.entity.Airline;
import com.acroteq.ticketing.order.service.domain.entity.Flight;
import com.acroteq.ticketing.order.service.domain.ports.output.repository.AirlineFlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.TestComponent;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@TestComponent
public class AirlineTestDataHelper {

  public static final Long AIRLINE_ID = IdTestDataHelper.nextId();

  public static final Airline AIRLINE = Airline.builder()
                                               .id(AirlineId.of(AIRLINE_ID))
                                               .active(true)
                                               .flights(List.of(new Flight[]{ FlightTestDataHelper.FLIGHT_1,
                                                                              FlightTestDataHelper.FLIGHT_2,
                                                                              FlightTestDataHelper.FLIGHT_3 }))
                                               .build();


  private final AirlineFlightRepository airlineFlightRepository;

  public void initialiseMocks() {
    lenient().when(airlineFlightRepository.loadAirline(AirlineId.of(AIRLINE_ID)))
             .thenReturn(Optional.of(AIRLINE));
  }
}
