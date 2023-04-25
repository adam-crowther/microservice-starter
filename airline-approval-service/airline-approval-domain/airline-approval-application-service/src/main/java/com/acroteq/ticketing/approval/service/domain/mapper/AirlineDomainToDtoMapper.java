package com.acroteq.ticketing.approval.service.domain.mapper;

import com.acroteq.ticketing.application.mapper.AirlineIdMapper;
import com.acroteq.ticketing.approval.service.domain.dto.AirlineDto;
import com.acroteq.ticketing.approval.service.domain.entity.airline.Airline;
import org.mapstruct.Mapper;

@Mapper(uses = { AirlineIdMapper.class, FlightDomainToDtoMapper.class })
public interface AirlineDomainToDtoMapper {

  AirlineDto convertDomainToDto(Airline airline);
}
