package com.acroteq.ticketing.airline.service.data.access.airline.adapter;

import com.acroteq.ticketing.airline.service.data.access.airline.mapper.FlightJpaToDomainMapper;
import com.acroteq.ticketing.airline.service.data.access.airline.repository.FlightJpaRepository;
import com.acroteq.ticketing.airline.service.domain.entity.Flight;
import com.acroteq.ticketing.airline.service.domain.ports.output.repository.FlightRepository;
import com.acroteq.ticketing.domain.valueobject.FlightId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class FlightRepositoryImpl implements FlightRepository {

  private final FlightJpaRepository flightJpaRepository;
  private final FlightJpaToDomainMapper jpaToDomainMapper;

  @Override
  public Optional<Flight> findById(final FlightId flightId) {
    return flightJpaRepository.findById(flightId.getValue())
                              .map(jpaToDomainMapper::convertJpaToDomain);
  }
}
