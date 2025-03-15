package com.acroteq.ticketing.approval.service.domain.resolver;

import com.acroteq.application.resolver.Resolver;
import com.acroteq.domain.valueobject.FlightId;
import com.acroteq.ticketing.approval.service.domain.entity.airline.Flight;
import com.acroteq.ticketing.approval.service.domain.exception.FlightNotFoundException;
import com.acroteq.ticketing.approval.service.domain.ports.output.repository.FlightRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class FlightResolver implements Resolver<FlightId, Flight> {

  private final FlightRepository flightRepository;

  @Override
  public Flight resolve(final FlightId id) {
    return flightRepository.findById(id)
                           .orElseThrow(() -> new FlightNotFoundException(id));
  }

  @Override
  public Flight resolve(final Long id) {
    final FlightId flightId = FlightId.of(id);
    return resolve(flightId);
  }
}
