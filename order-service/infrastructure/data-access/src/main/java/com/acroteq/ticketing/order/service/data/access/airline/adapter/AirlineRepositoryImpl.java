package com.acroteq.ticketing.order.service.data.access.airline.adapter;

import com.acroteq.domain.valueobject.AirlineId;
import com.acroteq.infrastructure.data.access.repository.ReadWriteRepositoryImpl;
import com.acroteq.ticketing.order.service.data.access.airline.entity.AirlineJpaEntity;
import com.acroteq.ticketing.order.service.data.access.airline.mapper.AirlineDomainToJpaMapper;
import com.acroteq.ticketing.order.service.data.access.airline.mapper.AirlineJpaToDomainMapper;
import com.acroteq.ticketing.order.service.data.access.airline.repository.AirlineJpaRepository;
import com.acroteq.ticketing.order.service.domain.entity.Airline;
import com.acroteq.ticketing.order.service.domain.ports.output.repository.AirlineRepository;
import org.springframework.stereotype.Component;

@Component
public class AirlineRepositoryImpl extends ReadWriteRepositoryImpl<AirlineId, Airline, AirlineJpaEntity>
    implements AirlineRepository {

  private final AirlineJpaRepository jpaRepository;

  public AirlineRepositoryImpl(
      final AirlineJpaRepository jpaRepository, final AirlineJpaToDomainMapper jpaToDomainMapper,
      final AirlineDomainToJpaMapper domainToJpaMapper) {
    super(jpaRepository, jpaToDomainMapper, domainToJpaMapper);

    this.jpaRepository = jpaRepository;
  }

  @Override
  public void deleteByCode(final String code) {
    jpaRepository.deleteByCode(code);
  }
}