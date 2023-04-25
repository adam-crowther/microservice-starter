package com.acroteq.ticketing.order.service.data.access.airline.adapter;

import com.acroteq.ticketing.domain.valueobject.FlightId;
import com.acroteq.ticketing.order.service.data.access.airline.mapper.FlightJpaToDomainMapper;
import com.acroteq.ticketing.order.service.data.access.airline.repository.FlightJpaRepository;
import com.acroteq.ticketing.order.service.domain.entity.Flight;
import com.acroteq.ticketing.order.service.domain.ports.output.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class FlightRepositoryImpl implements FlightRepository {

  private final FlightJpaRepository jpaRepository;
  private final FlightJpaToDomainMapper flightJpaToDomainMapper;

  @Override
  public Optional<Flight> findFlight(final FlightId flightId) {
    final Long id = flightId.getValue();
    return jpaRepository.findById(id)
                        .map(flightJpaToDomainMapper::convertJpaToDomain);
  }
}
