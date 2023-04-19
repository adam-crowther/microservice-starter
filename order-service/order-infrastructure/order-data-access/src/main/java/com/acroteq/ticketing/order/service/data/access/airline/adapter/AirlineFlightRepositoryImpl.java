package com.acroteq.ticketing.order.service.data.access.airline.adapter;

import com.acroteq.ticketing.domain.valueobject.AirlineId;
import com.acroteq.ticketing.domain.valueobject.FlightId;
import com.acroteq.ticketing.order.service.data.access.airline.mapper.AirlineJpaToDomainMapper;
import com.acroteq.ticketing.order.service.data.access.airline.mapper.AirlineJpaToFlightDomainMapper;
import com.acroteq.ticketing.order.service.data.access.airline.repository.AirlineJpaRepository;
import com.acroteq.ticketing.order.service.domain.entity.Airline;
import com.acroteq.ticketing.order.service.domain.entity.Flight;
import com.acroteq.ticketing.order.service.domain.ports.output.repository.AirlineFlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class AirlineFlightRepositoryImpl implements AirlineFlightRepository {

  private final AirlineJpaRepository jpaRepository;
  private final AirlineJpaToDomainMapper airlineMapper;
  private final AirlineJpaToFlightDomainMapper flightMapper;

  @Override
  public Optional<Airline> loadAirline(final AirlineId airlineId) {
    final Long id = airlineId.getValue();

    return jpaRepository.findByAirlineId(id)
                        .map(airlineMapper::convertEntityToDomain);
  }

  @Override
  public Optional<Flight> loadFlight(final FlightId flightId) {
    final Long id = flightId.getValue();

    return jpaRepository.findByFlightId(id)
                        .map(flightMapper::convertEntityToDomain);
  }
}
