package com.acroteq.ticketing.order.service.domain.resolver;

import com.acroteq.ticketing.application.resolver.Resolver;
import com.acroteq.ticketing.domain.valueobject.AirlineId;
import com.acroteq.ticketing.order.service.domain.entity.Airline;
import com.acroteq.ticketing.order.service.domain.exception.AirlineNotFoundException;
import com.acroteq.ticketing.order.service.domain.ports.output.repository.AirlineFlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AirlineResolver implements Resolver<AirlineId, Airline> {

  private final AirlineFlightRepository airlineFlightRepository;

  @Override
  public Airline resolve(final AirlineId id) {
    return airlineFlightRepository.loadAirline(id)
                                  .orElseThrow(() -> new AirlineNotFoundException(id));
  }

  @Override
  public Airline resolve(final Long id) {
    final AirlineId airlineId = AirlineId.of(id);
    return resolve(airlineId);
  }
}
