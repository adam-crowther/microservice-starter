package com.acroteq.ticketing.airline.service.data.access.airline.adapter;

import com.acroteq.domain.valueobject.FlightId;
import com.acroteq.infrastructure.data.access.repository.ReadWriteRepositoryImpl;
import com.acroteq.ticketing.airline.service.data.access.airline.entity.FlightJpaEntity;
import com.acroteq.ticketing.airline.service.data.access.airline.mapper.FlightDomainToJpaMapper;
import com.acroteq.ticketing.airline.service.data.access.airline.mapper.FlightJpaToDomainMapper;
import com.acroteq.ticketing.airline.service.data.access.airline.repository.FlightJpaRepository;
import com.acroteq.ticketing.airline.service.domain.entity.Flight;
import com.acroteq.ticketing.airline.service.domain.ports.output.repository.FlightRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FlightRepositoryImpl extends ReadWriteRepositoryImpl<FlightId, Flight, FlightJpaEntity>
    implements FlightRepository {

  private final FlightJpaRepository jpaRepository;
  private final FlightJpaToDomainMapper jpaToDomainMapper;

  public FlightRepositoryImpl(
      final FlightJpaRepository jpaRepository, final FlightJpaToDomainMapper jpaToDomainMapper,
      final FlightDomainToJpaMapper domainToJpaMapper) {
    super(jpaRepository, jpaToDomainMapper, domainToJpaMapper);

    this.jpaRepository = jpaRepository;
    this.jpaToDomainMapper = jpaToDomainMapper;
  }

  @Override
  public Optional<Flight> findByCode(final String code) {
    return jpaRepository.findByCode(code)
                        .map(jpaToDomainMapper::convertJpaToDomain);

  }

  @Override
  public Optional<FlightId> findIdByCode(final String code) {
    return jpaRepository.findIdByCode(code)
                        .map(FlightId::of);
  }

  @Override
  public void deleteByCode(final String code) {
    jpaRepository.deleteByCode(code);
  }
}
