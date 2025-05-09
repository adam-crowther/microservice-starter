package com.acroteq.ticketing.airline.service.data.access.airline.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.AirlineIdMapper;
import com.acroteq.infrastructure.mapper.JpaMapper;
import com.acroteq.ticketing.airline.service.data.access.airline.entity.AirlineJpaEntity;
import com.acroteq.ticketing.airline.service.data.access.airline.repository.AirlineJpaRepository;
import com.acroteq.ticketing.airline.service.domain.entity.Airline;
import lombok.Getter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(config = MapstructConfig.class, uses = { AirlineIdMapper.class, FlightJpaMapper.class })
public abstract class AirlineJpaMapper implements JpaMapper<Airline, AirlineJpaEntity> {

  @Getter
  @Autowired
  private AirlineJpaRepository repository;

  @Override
  public abstract Airline convert(AirlineJpaEntity entity);

  @Mapping(target = "audit", ignore = true)
  @Override
  public abstract AirlineJpaEntity convertNew(Airline entity);

  @Mapping(target = "audit", ignore = true)
  @Override
  public abstract AirlineJpaEntity convertExisting(Airline entity, @MappingTarget AirlineJpaEntity jpaEntity);
}
