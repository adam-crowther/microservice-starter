package com.acroteq.ticketing.airline.service.domain.mapper;

import com.acroteq.ticketing.airline.service.domain.dto.get.AirlineDto;
import com.acroteq.ticketing.airline.service.domain.entity.Airline;
import com.acroteq.ticketing.application.mapper.DomainToDtoMapper;
import com.acroteq.ticketing.application.mapper.id.AirlineIdMapper;
import org.mapstruct.Mapper;

@Mapper(uses = { AirlineIdMapper.class, FlightDomainToDtoMapper.class })
public interface AirlineDomainToDtoMapper extends DomainToDtoMapper<Airline, AirlineDto> {

  @Override
  AirlineDto convertDomainToDto(Airline airline);
}
