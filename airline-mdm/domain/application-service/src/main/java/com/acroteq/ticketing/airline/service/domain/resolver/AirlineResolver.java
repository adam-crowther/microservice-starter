package com.acroteq.ticketing.airline.service.domain.resolver;

import com.acroteq.application.resolver.Resolver;
import com.acroteq.domain.valueobject.AirlineId;
import com.acroteq.ticketing.airline.service.domain.entity.Airline;
import com.acroteq.ticketing.airline.service.domain.exception.AirlineNotFoundException;
import com.acroteq.ticketing.airline.service.domain.ports.output.repository.AirlineRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class AirlineResolver implements Resolver<AirlineId, Airline> {

  private final AirlineRepository airlineRepository;

  @Override
  public Airline resolve(final AirlineId id) {
    return airlineRepository.findById(id)
                            .orElseThrow(() -> new AirlineNotFoundException(id));
  }

  @Override
  public Airline resolve(final Long id) {
    final AirlineId airlineId = AirlineId.of(id);
    return resolve(airlineId);
  }
}
