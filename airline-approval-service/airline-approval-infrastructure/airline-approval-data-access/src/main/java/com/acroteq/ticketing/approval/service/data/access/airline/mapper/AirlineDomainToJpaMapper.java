package com.acroteq.ticketing.approval.service.data.access.airline.mapper;

import com.acroteq.ticketing.application.mapper.id.AirlineIdMapper;
import com.acroteq.ticketing.approval.service.data.access.airline.entity.AirlineJpaEntity;
import com.acroteq.ticketing.approval.service.domain.entity.airline.Airline;
import com.acroteq.ticketing.infrastructure.mapper.DomainToJpaMapper;
import org.mapstruct.Mapper;

@Mapper(uses = { AirlineIdMapper.class, FlightDomainToJpaMapper.class })
public interface AirlineDomainToJpaMapper extends DomainToJpaMapper<Airline, AirlineJpaEntity> {

  @Override
  AirlineJpaEntity convertDomainToJpa(Airline entity);
}
