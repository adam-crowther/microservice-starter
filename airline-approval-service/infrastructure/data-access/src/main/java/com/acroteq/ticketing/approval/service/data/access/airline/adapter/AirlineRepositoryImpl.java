package com.acroteq.ticketing.approval.service.data.access.airline.adapter;

import com.acroteq.domain.valueobject.AirlineId;
import com.acroteq.infrastructure.data.access.repository.ReadWriteRepositoryImpl;
import com.acroteq.ticketing.approval.service.data.access.airline.entity.AirlineJpaEntity;
import com.acroteq.ticketing.approval.service.data.access.airline.mapper.AirlineDomainToJpaMapper;
import com.acroteq.ticketing.approval.service.data.access.airline.mapper.AirlineJpaToDomainMapper;
import com.acroteq.ticketing.approval.service.data.access.airline.repository.AirlineJpaRepository;
import com.acroteq.ticketing.approval.service.domain.entity.airline.Airline;
import com.acroteq.ticketing.approval.service.domain.ports.output.repository.AirlineRepository;
import org.springframework.stereotype.Component;

@Component
public class AirlineRepositoryImpl extends ReadWriteRepositoryImpl<AirlineId, Airline, AirlineJpaEntity>
    implements AirlineRepository {

  public AirlineRepositoryImpl(final AirlineJpaRepository jpaRepository,
                               final AirlineJpaToDomainMapper jpaToDomainMapper,
                               final AirlineDomainToJpaMapper domainToJpaMapper) {
    super(jpaRepository, jpaToDomainMapper, domainToJpaMapper);
  }
}
