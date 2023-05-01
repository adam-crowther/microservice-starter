package com.acroteq.ticketing.approval.service.domain.resolver;

import com.acroteq.ticketing.application.resolver.Resolver;
import com.acroteq.ticketing.approval.service.domain.entity.airline.Airline;
import com.acroteq.ticketing.approval.service.domain.exception.AirlineNotFoundException;
import com.acroteq.ticketing.approval.service.domain.ports.output.repository.AirlineRepository;
import com.acroteq.ticketing.domain.valueobject.AirlineId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
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
