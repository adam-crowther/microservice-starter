package com.acroteq.ticketing.airline.service.data.access.airline.mapper;

import com.acroteq.ticketing.airline.service.data.access.airline.entity.AirlineJpaEntity;
import com.acroteq.ticketing.airline.service.domain.entity.Airline;
import com.acroteq.ticketing.application.mapper.AirlineIdMapper;
import org.mapstruct.Mapper;

@Mapper(uses = { AirlineIdMapper.class, FlightJpaToDomainMapper.class })
public interface AirlineJpaToDomainMapper {

  /* package */
  Airline convertJpaToDomain(AirlineJpaEntity entity);
}
