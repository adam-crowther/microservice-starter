package com.acroteq.ticketing.order.service.domain.test.helper;

import static org.mockito.Mockito.lenient;

import com.acroteq.domain.valueobject.AirlineId;
import com.acroteq.domain.valueobject.EventId;
import com.acroteq.ticketing.order.service.domain.entity.Airline;
import com.acroteq.ticketing.order.service.domain.ports.output.repository.AirlineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.TestComponent;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@TestComponent
public class AirlineTestDataHelper {

  public static final Long AIRLINE_ID = IdTestDataHelper.nextId();
  public static final String AIRLINE_NAME = "airline-name";

  public static final Airline AIRLINE = Airline.builder()
                                               .id(AirlineId.of(AIRLINE_ID))
                                               .version(0L)
                                               .eventId(EventId.builder()
                                                               .partition(0)
                                                               .offset(0L)
                                                               .build())
                                               .name(AIRLINE_NAME)
                                               .active(true)
                                               .flights(List.of(FlightTestDataHelper.FLIGHT_1,
                                                                FlightTestDataHelper.FLIGHT_2,
                                                                FlightTestDataHelper.FLIGHT_3))
                                               .build();


  private final AirlineRepository airlineRepository;

  public void initialiseMocks() {
    lenient().when(airlineRepository.findById(AirlineId.of(AIRLINE_ID)))
             .thenReturn(Optional.of(AIRLINE));
  }
}
