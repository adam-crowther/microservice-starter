package com.acroteq.ticketing.order.service.domain.resolver;

import com.acroteq.application.resolver.Resolver;
import com.acroteq.domain.valueobject.FlightId;
import com.acroteq.ticketing.order.service.domain.entity.Flight;
import com.acroteq.ticketing.order.service.domain.exception.FlightNotFoundException;
import com.acroteq.ticketing.order.service.domain.ports.output.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class FlightResolver implements Resolver<FlightId, Flight> {

  private final FlightRepository flightRepository;

  @Override
  public Flight resolve(final FlightId flightId) {
    return flightRepository.findById(flightId)
                           .orElseThrow(() -> new FlightNotFoundException(flightId));
  }

  @Override
  public Flight resolve(final Long id) {
    final FlightId flightId = FlightId.of(id);
    return resolve(flightId);
  }
}
