package com.acroteq.ticketing.airline.service.data.access.airline.adapter;

import com.acroteq.domain.valueobject.AirlineId;
import com.acroteq.infrastructure.data.access.repository.ReadWriteRepositoryImpl;
import com.acroteq.ticketing.airline.service.data.access.airline.entity.AirlineJpaEntity;
import com.acroteq.ticketing.airline.service.data.access.airline.mapper.AirlineDomainToJpaMapper;
import com.acroteq.ticketing.airline.service.data.access.airline.mapper.AirlineJpaToDomainMapper;
import com.acroteq.ticketing.airline.service.data.access.airline.repository.AirlineJpaRepository;
import com.acroteq.ticketing.airline.service.domain.entity.Airline;
import com.acroteq.ticketing.airline.service.domain.ports.output.repository.AirlineRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AirlineRepositoryImpl extends ReadWriteRepositoryImpl<AirlineId, Airline, AirlineJpaEntity>
    implements AirlineRepository {

  private final AirlineJpaRepository jpaRepository;
  private final AirlineJpaToDomainMapper jpaToDomainMapper;

  public AirlineRepositoryImpl(
      final AirlineJpaRepository jpaRepository, final AirlineJpaToDomainMapper jpaToDomainMapper,
      final AirlineDomainToJpaMapper domainToJpaMapper) {
    super(jpaRepository, jpaToDomainMapper, domainToJpaMapper);

    this.jpaRepository = jpaRepository;
    this.jpaToDomainMapper = jpaToDomainMapper;
  }

  @Override
  public Optional<Airline> findByCode(final String code) {
    return jpaRepository.findByCode(code)
                        .map(jpaToDomainMapper::convertJpaToDomain);
  }

  @Override
  public Optional<AirlineId> findIdByCode(final String code) {
    return jpaRepository.findIdByCode(code)
                        .map(AirlineId::of);
  }

  @Override
  public void deleteByCode(final String code) {
    jpaRepository.deleteByCode(code);
  }
}
