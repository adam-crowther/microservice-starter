package com.acroteq.ticketing.order.service.data.access.airline.adapter;

import com.acroteq.ticketing.domain.valueobject.AirlineId;
import com.acroteq.ticketing.order.service.data.access.airline.entity.AirlineJpaEntity;
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
  private final AirlineJpaToDomainMapper airlineJpaToDomainMapper;
  private final AirlineDomainToJpaMapper airlineDomainToJpaMapper;

  @Override
  public Optional<Airline> findAirline(final AirlineId airlineId) {
    final Long id = airlineId.getValue();
    return jpaRepository.findById(id)
                        .map(airlineJpaToDomainMapper::convertJpaToDomain);
  }

  @Override
  public Airline save(final Airline airline) {
    final AirlineJpaEntity airlineFlightJpaEntity = airlineDomainToJpaMapper.convertDomainToJpa(airline);
    final AirlineJpaEntity savedAirlineFlightJpaEntity = jpaRepository.save(airlineFlightJpaEntity);
    return airlineJpaToDomainMapper.convertJpaToDomain(savedAirlineFlightJpaEntity);
  }

  @Override
  public void delete(final AirlineId airlineId) {
    final Long id = airlineId.getValue();
    jpaRepository.deleteById(id);
  }
}
