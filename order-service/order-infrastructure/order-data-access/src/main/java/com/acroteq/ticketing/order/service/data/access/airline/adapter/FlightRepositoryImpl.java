package com.acroteq.ticketing.order.service.data.access.airline.adapter;

import com.acroteq.ticketing.domain.valueobject.FlightId;
import com.acroteq.ticketing.infrastructure.data.access.repository.ReadRepositoryImpl;
import com.acroteq.ticketing.order.service.data.access.airline.entity.FlightJpaEntity;
import com.acroteq.ticketing.order.service.data.access.airline.mapper.FlightJpaToDomainMapper;
import com.acroteq.ticketing.order.service.data.access.airline.repository.FlightJpaRepository;
import com.acroteq.ticketing.order.service.domain.entity.Flight;
import com.acroteq.ticketing.order.service.domain.ports.output.repository.FlightRepository;
import org.springframework.stereotype.Component;

@Component
public class FlightRepositoryImpl extends ReadRepositoryImpl<FlightId, Flight, FlightJpaEntity>
    implements FlightRepository {

  public FlightRepositoryImpl(final FlightJpaRepository jpaRepository,
                              final FlightJpaToDomainMapper jpaToDomainMapper) {
    super(jpaRepository, jpaToDomainMapper);
  }
}
