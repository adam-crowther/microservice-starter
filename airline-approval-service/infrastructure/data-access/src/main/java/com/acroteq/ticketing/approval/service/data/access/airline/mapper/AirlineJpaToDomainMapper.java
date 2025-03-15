package com.acroteq.ticketing.approval.service.data.access.airline.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.AirlineIdMapper;
import com.acroteq.infrastructure.mapper.JpaToDomainMapper;
import com.acroteq.ticketing.approval.service.data.access.airline.entity.AirlineJpaEntity;
import com.acroteq.ticketing.approval.service.domain.entity.airline.Airline;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class, uses = { AirlineIdMapper.class, FlightJpaToDomainMapper.class })
public interface AirlineJpaToDomainMapper extends JpaToDomainMapper<AirlineJpaEntity, Airline> {

  @Override
  Airline convertJpaToDomain(AirlineJpaEntity entity);
}
