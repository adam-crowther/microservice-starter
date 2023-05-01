package com.acroteq.ticketing.order.service.data.access.airline.adapter;

import com.acroteq.ticketing.domain.valueobject.AirlineId;
import com.acroteq.ticketing.order.service.data.access.airline.entity.AirlineJpaEntity;
import com.acroteq.ticketing.order.service.data.access.airline.exception.AirlineAlreadyExistsException;
import com.acroteq.ticketing.order.service.data.access.airline.exception.AirlineMissingIdException;
import com.acroteq.ticketing.order.service.data.access.airline.mapper.AirlineDomainToJpaMapper;
import com.acroteq.ticketing.order.service.data.access.airline.mapper.AirlineJpaToDomainMapper;
import com.acroteq.ticketing.order.service.data.access.airline.repository.AirlineJpaRepository;
import com.acroteq.ticketing.order.service.domain.entity.Airline;
import com.acroteq.ticketing.order.service.domain.ports.output.repository.AirlineRepository;
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
    final Long id = airlineId.getValue();
    return jpaRepository.findById(id)
                        .map(jpaToDomainMapper::convertJpaToDomain);
  }

  @Override
  public Airline insert(final Airline airline) {
    final AirlineId airlineId = airline.getId();
    checkDoesNotAlreadyExist(airlineId);
    final AirlineJpaEntity airlineFlightJpaEntity = domainToJpaMapper.convertDomainToJpa(airline);
    final AirlineJpaEntity savedAirlineFlightJpaEntity = jpaRepository.save(airlineFlightJpaEntity);
    return jpaToDomainMapper.convertJpaToDomain(savedAirlineFlightJpaEntity);
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
    final AirlineJpaEntity airlineFlightJpaEntity = domainToJpaMapper.convertDomainToJpa(airline);
    final AirlineJpaEntity savedAirlineFlightJpaEntity = jpaRepository.save(airlineFlightJpaEntity);
    return jpaToDomainMapper.convertJpaToDomain(savedAirlineFlightJpaEntity);
  }

  @Override
  public void deleteById(final AirlineId airlineId) {
    final Long id = airlineId.getValue();
    jpaRepository.deleteById(id);
  }
}
