package com.acroteq.ticketing.airline.service.domain.mapper;

import com.acroteq.application.mapper.DomainToDtoMapper;
import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.AirlineIdMapper;
import com.acroteq.ticketing.airline.service.domain.dto.get.AirlineDto;
import com.acroteq.ticketing.airline.service.domain.entity.Airline;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class, uses = { AirlineIdMapper.class, FlightDomainToDtoMapper.class })
public interface AirlineDomainToDtoMapper extends DomainToDtoMapper<Airline, AirlineDto> {

  @Override
  AirlineDto convertDomainToDto(Airline airline);
}
