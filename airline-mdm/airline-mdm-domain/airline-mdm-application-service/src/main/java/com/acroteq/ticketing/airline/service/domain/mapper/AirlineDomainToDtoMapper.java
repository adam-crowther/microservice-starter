package com.acroteq.ticketing.airline.service.domain.mapper;

import com.acroteq.ticketing.airline.service.domain.dto.get.AirlineDto;
import com.acroteq.ticketing.airline.service.domain.entity.Airline;
import com.acroteq.ticketing.application.mapper.AirlineIdMapper;
import org.mapstruct.Mapper;

@Mapper(uses = { AirlineIdMapper.class, FlightDomainToDtoMapper.class })
public interface AirlineDomainToDtoMapper {

  AirlineDto convertDomainToDto(Airline airline);
}
