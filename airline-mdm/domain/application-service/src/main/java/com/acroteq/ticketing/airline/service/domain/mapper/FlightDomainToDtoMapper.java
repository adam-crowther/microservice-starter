package com.acroteq.ticketing.airline.service.domain.mapper;

import com.acroteq.ticketing.airline.service.domain.dto.get.FlightDto;
import com.acroteq.ticketing.airline.service.domain.entity.Flight;
import com.acroteq.ticketing.application.mapper.DomainToDtoMapper;
import com.acroteq.ticketing.application.mapper.MapstructConfig;
import com.acroteq.ticketing.application.mapper.id.FlightIdMapper;
import com.google.common.collect.ImmutableList;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfig.class, uses = { FlightIdMapper.class })
public interface FlightDomainToDtoMapper extends DomainToDtoMapper<Flight, FlightDto> {

  @Override
  FlightDto convertDomainToDto(Flight flight);

  List<FlightDto> convertDomainToDto(ImmutableList<Flight> flight);
}
