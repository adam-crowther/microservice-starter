package com.acroteq.ticketing.airline.service.presentation.mapper;

import com.acroteq.ticketing.airline.service.domain.dto.get.FlightDto;
import com.acroteq.ticketing.airline.service.presentation.model.Flight;
import com.acroteq.ticketing.application.mapper.id.AirlineIdMapper;
import com.acroteq.ticketing.common.application.mapper.DtoToApiMapper;
import com.google.common.collect.ImmutableList;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = { AirlineIdMapper.class, CashValueDtoToApiMapper.class })
public interface FlightDtoToApiMapper extends DtoToApiMapper<FlightDto, Flight> {

  @Override
  Flight convertDtoToApi(FlightDto dto);

  List<Flight> convertDtoToApi(ImmutableList<FlightDto> dto);
}
