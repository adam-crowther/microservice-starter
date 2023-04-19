package com.acroteq.ticketing.airline.service.domain.resolver;

import com.acroteq.ticketing.airline.service.domain.entity.Flight;
import com.acroteq.ticketing.airline.service.domain.exception.FlightNotFoundException;
import com.acroteq.ticketing.airline.service.domain.ports.output.repository.FlightRepository;
import com.acroteq.ticketing.application.resolver.Resolver;
import com.acroteq.ticketing.domain.valueobject.FlightId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class FlightResolver implements Resolver<FlightId, Flight> {

  private final FlightRepository flightRepository;

  @Override
  public Flight resolve(final FlightId id) {
    return flightRepository.loadFlight(id)
                           .orElseThrow(() -> new FlightNotFoundException(id));
  }

  @Override
  public Flight resolve(final Long id) {
    final FlightId flightId = FlightId.of(id);
    return resolve(flightId);
  }
}
