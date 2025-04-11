package com.acroteq.ticketing.airline.service.domain.resolver;

import com.acroteq.application.resolver.Resolver;
import com.acroteq.ticketing.airline.service.domain.entity.Flight;
import com.acroteq.ticketing.airline.service.domain.exception.FlightNotFoundException;
import com.acroteq.ticketing.airline.service.domain.ports.output.repository.FlightRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class FlightResolver implements Resolver<String, Flight> {

  private final FlightRepository flightRepository;

  @Override
  public Flight resolve(final String code) {
    return flightRepository.findByCode(code)
                           .orElseThrow(() -> new FlightNotFoundException(code));
  }
}
