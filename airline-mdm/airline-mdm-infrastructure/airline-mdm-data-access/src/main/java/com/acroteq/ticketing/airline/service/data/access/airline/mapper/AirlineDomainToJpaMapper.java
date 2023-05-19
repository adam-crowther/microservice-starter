package com.acroteq.ticketing.airline.service.data.access.airline.mapper;

import com.acroteq.ticketing.airline.service.data.access.airline.entity.AirlineJpaEntity;
import com.acroteq.ticketing.airline.service.domain.entity.Airline;
import com.acroteq.ticketing.application.mapper.MapstructConfig;
import com.acroteq.ticketing.application.mapper.id.AirlineIdMapper;
import com.acroteq.ticketing.infrastructure.mapper.DomainToJpaMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapstructConfig.class, uses = { AirlineIdMapper.class, FlightDomainToJpaMapper.class })
public interface AirlineDomainToJpaMapper extends DomainToJpaMapper<Airline, AirlineJpaEntity> {

  @Mapping(target = "audit", ignore = true)
  @Override
  AirlineJpaEntity convertDomainToJpa(Airline entity);

  @Mapping(target = "audit", ignore = true)
  @Override
  AirlineJpaEntity convertDomainToJpa(Airline entity, @MappingTarget AirlineJpaEntity jpaEntity);
}
