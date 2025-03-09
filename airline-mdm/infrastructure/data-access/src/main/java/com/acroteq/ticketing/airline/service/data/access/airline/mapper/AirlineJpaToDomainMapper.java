package com.acroteq.ticketing.airline.service.data.access.airline.mapper;

import com.acroteq.ticketing.airline.service.data.access.airline.entity.AirlineJpaEntity;
import com.acroteq.ticketing.airline.service.domain.entity.Airline;
import com.acroteq.ticketing.application.mapper.MapstructConfig;
import com.acroteq.ticketing.application.mapper.id.AirlineIdMapper;
import com.acroteq.ticketing.infrastructure.mapper.JpaToDomainMapper;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class, uses = { AirlineIdMapper.class, FlightJpaToDomainMapper.class })
public interface AirlineJpaToDomainMapper extends JpaToDomainMapper<AirlineJpaEntity, Airline> {

  @Override
  Airline convertJpaToDomain(AirlineJpaEntity entity);
}
