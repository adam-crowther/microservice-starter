package com.acroteq.ticketing.order.service.data.access.airline.mapper;

import static com.acroteq.ticketing.helper.StreamHelper.toSingleItem;

import com.acroteq.ticketing.domain.valueobject.AirlineId;
import com.acroteq.ticketing.order.service.data.access.airline.entity.AirlineJpaEntity;
import com.acroteq.ticketing.order.service.data.access.airline.exception.AirlineDataAccessException;
import com.acroteq.ticketing.order.service.domain.entity.Airline;
import com.acroteq.ticketing.order.service.domain.entity.Flight;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * We only use MapStruct where it simplifies things.  In this case it's easier to just make a component.
 */
@RequiredArgsConstructor
@Component
public class AirlineJpaToDomainMapper {

  private final AirlineJpaToFlightDomainMapper flightMapper;

  public Airline convertEntityToDomain(final List<AirlineJpaEntity> airlines) {
    final AirlineId airlineId = airlines.stream()
                                        .map(AirlineJpaEntity::getAirlineId)
                                        .distinct()
                                        .reduce(toSingleItem())
                                        .map(AirlineId::of)
                                        .orElseThrow(AirlineDataAccessException::new);

    final boolean airlineActive = airlines.stream()
                                          .allMatch(AirlineJpaEntity::isAirlineActive);

    final List<Flight> flights = airlines.stream()
                                         .map(flightMapper::convertEntityToDomain)
                                         .toList();

    return Airline.builder()
                  .id(airlineId)
                  .active(airlineActive)
                  .flights(flights)
                  .build();
  }
}
