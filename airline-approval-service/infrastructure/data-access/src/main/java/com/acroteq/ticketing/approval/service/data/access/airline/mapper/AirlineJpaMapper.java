package com.acroteq.ticketing.approval.service.data.access.airline.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.AirlineIdMapper;
import com.acroteq.infrastructure.mapper.EventIdJpaMapper;
import com.acroteq.infrastructure.mapper.JpaMapper;
import com.acroteq.ticketing.approval.service.data.access.airline.entity.AirlineJpaEntity;
import com.acroteq.ticketing.approval.service.data.access.airline.repository.AirlineJpaRepository;
import com.acroteq.ticketing.approval.service.domain.entity.airline.Airline;
import lombok.Getter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(config = MapstructConfig.class, uses = { AirlineIdMapper.class, FlightJpaMapper.class, EventIdJpaMapper.class })
public abstract class AirlineJpaMapper implements JpaMapper<Airline, AirlineJpaEntity> {

  @Getter
  @Autowired
  private AirlineJpaRepository repository;

  @Override
  public abstract Airline convert(AirlineJpaEntity entity);

  @Override
  public abstract AirlineJpaEntity convertNew(Airline entity);

  @Mapping(target = "version", source = "entity.version")
  @Mapping(target = "eventId", source = "entity.eventId")
  @Override
  public abstract AirlineJpaEntity convertExisting(Airline entity, @MappingTarget AirlineJpaEntity jpaEntity);
}
