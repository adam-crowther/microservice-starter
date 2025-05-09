package com.acroteq.ticketing.approval.service.data.access.airline.adapter;

import com.acroteq.domain.valueobject.FlightId;
import com.acroteq.infrastructure.data.access.repository.ReadRepositoryImpl;
import com.acroteq.ticketing.approval.service.data.access.airline.entity.FlightJpaEntity;
import com.acroteq.ticketing.approval.service.data.access.airline.mapper.FlightJpaMapper;
import com.acroteq.ticketing.approval.service.data.access.airline.repository.FlightJpaRepository;
import com.acroteq.ticketing.approval.service.domain.entity.airline.Flight;
import com.acroteq.ticketing.approval.service.domain.ports.output.repository.FlightRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FlightRepositoryImpl extends ReadRepositoryImpl<FlightId, Flight, FlightJpaEntity>
    implements FlightRepository {

  private final FlightJpaRepository jpaRepository;
  private final FlightJpaMapper jpaMapper;

  public FlightRepositoryImpl(final FlightJpaRepository jpaRepository, final FlightJpaMapper jpaMapper) {
    super(jpaRepository, jpaMapper);

    this.jpaRepository = jpaRepository;
    this.jpaMapper = jpaMapper;
  }

  @Override
  public Optional<Flight> findByCode(final String code) {
    return jpaRepository.findByCode(code)
                        .map(jpaMapper::convert);
  }
}
