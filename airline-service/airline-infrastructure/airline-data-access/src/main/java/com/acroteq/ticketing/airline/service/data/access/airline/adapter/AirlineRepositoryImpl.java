package com.acroteq.ticketing.airline.service.data.access.airline.adapter;

import com.acroteq.ticketing.airline.service.data.access.airline.mapper.AirlineJpaToDomainMapper;
import com.acroteq.ticketing.airline.service.data.access.airline.repository.AirlineJpaRepository;
import com.acroteq.ticketing.airline.service.domain.entity.Airline;
import com.acroteq.ticketing.airline.service.domain.ports.output.repository.AirlineRepository;
import com.acroteq.ticketing.domain.valueobject.AirlineId;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AirlineRepositoryImpl implements AirlineRepository {

  private final AirlineJpaRepository airlineJpaRepository;
  private final AirlineJpaToDomainMapper mapper;

  public AirlineRepositoryImpl(final AirlineJpaRepository airlineJpaRepository,
                               final AirlineJpaToDomainMapper airlineJpaToDomainMapper) {
    this.airlineJpaRepository = airlineJpaRepository;
    this.mapper = airlineJpaToDomainMapper;
  }

  @Override
  public Optional<Airline> loadAirline(final AirlineId airlineId) {
    return airlineJpaRepository.findById(airlineId.getValue())
                               .map(mapper::convertFlightEntityToDomain);
  }
}
