package com.acroteq.ticketing.order.service.data.access.airline.adapter;

import com.acroteq.domain.valueobject.AirlineId;
import com.acroteq.infrastructure.data.access.repository.ReadWriteRepositoryImpl;
import com.acroteq.ticketing.order.service.data.access.airline.entity.AirlineJpaEntity;
import com.acroteq.ticketing.order.service.data.access.airline.mapper.AirlineJpaMapper;
import com.acroteq.ticketing.order.service.data.access.airline.repository.AirlineJpaRepository;
import com.acroteq.ticketing.order.service.domain.entity.Airline;
import com.acroteq.ticketing.order.service.domain.ports.output.repository.AirlineRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AirlineRepositoryImpl extends ReadWriteRepositoryImpl<AirlineId, Airline, AirlineJpaEntity>
    implements AirlineRepository {

  private final AirlineJpaRepository jpaRepository;
  private final AirlineJpaMapper jpaMapper;

  public AirlineRepositoryImpl(final AirlineJpaRepository jpaRepository, final AirlineJpaMapper jpaMapper) {
    super(jpaRepository, jpaMapper);

    this.jpaRepository = jpaRepository;
    this.jpaMapper = jpaMapper;
  }

  @Override
  public void deleteByCode(final String code) {
    jpaRepository.deleteByCode(code);
  }

  @Override
  public Optional<Airline> findByCode(final String code) {
    return jpaRepository.findByCode(code)
                        .map(jpaMapper::convert);
  }
}