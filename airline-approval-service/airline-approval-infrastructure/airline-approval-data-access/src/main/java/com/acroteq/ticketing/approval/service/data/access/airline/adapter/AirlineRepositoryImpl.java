package com.acroteq.ticketing.approval.service.data.access.airline.adapter;

import com.acroteq.ticketing.approval.service.data.access.airline.entity.AirlineJpaEntity;
import com.acroteq.ticketing.approval.service.data.access.airline.exception.AirlineAlreadyExistsException;
import com.acroteq.ticketing.approval.service.data.access.airline.exception.AirlineMissingIdException;
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

  private final AirlineJpaRepository jpaRepository;
  private final AirlineJpaToDomainMapper jpaToDomainMapper;
  private final AirlineDomainToJpaMapper domainToJpaMapper;

  @Override
  public Optional<Airline> findById(final AirlineId airlineId) {
    return jpaRepository.findById(airlineId.getValue())
                        .map(jpaToDomainMapper::convertJpaToDomain);
  }

  @Override
  public Airline insert(final Airline airline) {
    final AirlineId airlineId = airline.getId();
    checkDoesNotAlreadyExist(airlineId);
    final AirlineJpaEntity airlineJpaEntity = domainToJpaMapper.convertDomainToJpa(airline);
    final AirlineJpaEntity savedEntity = jpaRepository.save(airlineJpaEntity);
    return jpaToDomainMapper.convertJpaToDomain(savedEntity);
  }

  private void checkDoesNotAlreadyExist(final AirlineId airlineId) {
    final Long id = Optional.ofNullable(airlineId)
                            .map(AirlineId::getValue)
                            .orElseThrow(AirlineMissingIdException::new);
    if (jpaRepository.existsById(id)) {
      throw new AirlineAlreadyExistsException(airlineId);
    }
  }

  @Override
  public Airline update(final Airline airline) {
    final AirlineJpaEntity airlineJpaEntity = domainToJpaMapper.convertDomainToJpa(airline);
    final AirlineJpaEntity savedEntity = jpaRepository.save(airlineJpaEntity);
    return jpaToDomainMapper.convertJpaToDomain(savedEntity);
  }

  @Override
  public void deleteById(final AirlineId airlineId) {
    jpaRepository.deleteById(airlineId.getValue());
  }
}
