package com.acroteq.ticketing.approval.service.data.access.airline.adapter;

import com.acroteq.ticketing.approval.service.data.access.airline.entity.AirlineJpaEntity;
import com.acroteq.ticketing.approval.service.data.access.airline.mapper.AirlineDomainToJpaMapper;
import com.acroteq.ticketing.approval.service.data.access.airline.mapper.AirlineJpaToDomainMapper;
import com.acroteq.ticketing.approval.service.data.access.airline.repository.AirlineJpaRepository;
import com.acroteq.ticketing.approval.service.domain.entity.airline.Airline;
import com.acroteq.ticketing.approval.service.domain.ports.output.repository.AirlineRepository;
import com.acroteq.ticketing.domain.valueobject.AirlineId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class AirlineRepositoryImpl implements AirlineRepository {

  private final AirlineJpaRepository airlineJpaRepository;
  private final AirlineJpaToDomainMapper jpaToDomainMapper;
  private final AirlineDomainToJpaMapper domainToJpaMapper;

  @Override
  public Optional<Airline> loadAirline(final AirlineId airlineId) {
    return airlineJpaRepository.findById(airlineId.getValue())
                               .map(jpaToDomainMapper::convertJpaToDomain);
  }

  @Override
  public Airline save(final Airline airline) {
    final AirlineJpaEntity airlineJpaEntity = domainToJpaMapper.convertDomainToJpa(airline);
    final AirlineJpaEntity savedEntity = airlineJpaRepository.save(airlineJpaEntity);
    return jpaToDomainMapper.convertJpaToDomain(savedEntity);
  }

  @Override
  public Optional<Airline> findById(final AirlineId airlineId) {
    return airlineJpaRepository.findById(airlineId.getValue())
                               .map(jpaToDomainMapper::convertJpaToDomain);
  }

  @Override
  public void deleteById(final AirlineId airlineId) {
    airlineJpaRepository.deleteById(airlineId.getValue());
  }
}
