package com.acroteq.ticketing.airline.service.domain.resolver;

import com.acroteq.application.resolver.Resolver;
import com.acroteq.ticketing.airline.service.domain.entity.Airline;
import com.acroteq.ticketing.airline.service.domain.exception.AirlineNotFoundException;
import com.acroteq.ticketing.airline.service.domain.ports.output.repository.AirlineRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class AirlineResolver implements Resolver<String, Airline> {

  private final AirlineRepository airlineRepository;

  @Override
  public Airline resolve(final String code) {
    return airlineRepository.findByCode(code)
                            .orElseThrow(() -> new AirlineNotFoundException(code));
  }
}
