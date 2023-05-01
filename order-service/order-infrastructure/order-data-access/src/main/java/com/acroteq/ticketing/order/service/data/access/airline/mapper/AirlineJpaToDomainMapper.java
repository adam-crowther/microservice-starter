package com.acroteq.ticketing.order.service.data.access.airline.mapper;

import com.acroteq.ticketing.domain.valueobject.AirlineId;
import com.acroteq.ticketing.infrastructure.mapper.JpaToDomainMapper;
import com.acroteq.ticketing.order.service.data.access.airline.entity.AirlineJpaEntity;
import com.acroteq.ticketing.order.service.domain.entity.Airline;
import org.mapstruct.Mapper;

@Mapper(uses = { AirlineId.class, FlightJpaToDomainMapper.class })
public interface AirlineJpaToDomainMapper extends JpaToDomainMapper<AirlineJpaEntity, Airline> {

  @Override
  Airline convertJpaToDomain(AirlineJpaEntity airline);
}
