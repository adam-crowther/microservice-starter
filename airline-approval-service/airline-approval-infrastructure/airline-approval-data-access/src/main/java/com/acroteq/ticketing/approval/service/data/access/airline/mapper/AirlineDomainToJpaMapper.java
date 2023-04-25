package com.acroteq.ticketing.approval.service.data.access.airline.mapper;

import com.acroteq.ticketing.application.mapper.AirlineIdMapper;
import com.acroteq.ticketing.approval.service.data.access.airline.entity.AirlineJpaEntity;
import com.acroteq.ticketing.approval.service.domain.entity.airline.Airline;
import org.mapstruct.Mapper;

@Mapper(uses = { AirlineIdMapper.class, FlightDomainToJpaMapper.class })
public interface AirlineDomainToJpaMapper {

  AirlineJpaEntity convertDomainToJpa(Airline entity);
}
