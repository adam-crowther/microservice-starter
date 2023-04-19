package com.acroteq.ticketing.order.service.domain.resolver;

import com.acroteq.ticketing.application.resolver.Resolver;
import com.acroteq.ticketing.domain.valueobject.FlightId;
import com.acroteq.ticketing.order.service.domain.entity.Flight;
import com.acroteq.ticketing.order.service.domain.exception.FlightNotFoundException;
import com.acroteq.ticketing.order.service.domain.ports.output.repository.AirlineFlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class FlightResolver implements Resolver<FlightId, Flight> {

  private final AirlineFlightRepository airlineFlightRepository;

  @Override
  public Flight resolve(final FlightId flightId) {
    return airlineFlightRepository.loadFlight(flightId)
                                  .orElseThrow(() -> new FlightNotFoundException(flightId));
  }

  @Override
  public Flight resolve(final Long id) {
    final FlightId flightId = FlightId.of(id);
    return resolve(flightId);
  }
}
