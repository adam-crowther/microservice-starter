package com.acroteq.ticketing.order.service.data.access.airline.mapper;

import com.acroteq.ticketing.application.mapper.MapstructConfig;
import com.acroteq.ticketing.application.mapper.id.AirlineIdMapper;
import com.acroteq.ticketing.infrastructure.mapper.DomainToJpaMapper;
import com.acroteq.ticketing.infrastructure.mapper.EventIdDomainToJpaMapper;
import com.acroteq.ticketing.order.service.data.access.airline.entity.AirlineJpaEntity;
import com.acroteq.ticketing.order.service.domain.entity.Airline;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapstructConfig.class,
        uses = { AirlineIdMapper.class, FlightDomainToJpaMapper.class, EventIdDomainToJpaMapper.class })
public interface AirlineDomainToJpaMapper extends DomainToJpaMapper<Airline, AirlineJpaEntity> {

  @Override
  AirlineJpaEntity convertDomainToJpa(Airline entity);

  @Mapping(target = "version", source = "entity.version")
  @Mapping(target = "eventId", source = "entity.eventId")
  @Override
  AirlineJpaEntity convertDomainToJpa(Airline entity, @MappingTarget AirlineJpaEntity jpaEntity);
}
