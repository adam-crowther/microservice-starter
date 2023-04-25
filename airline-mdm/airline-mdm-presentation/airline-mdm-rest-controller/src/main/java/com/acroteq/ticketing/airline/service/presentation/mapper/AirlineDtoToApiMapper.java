package com.acroteq.ticketing.airline.service.presentation.mapper;

import com.acroteq.ticketing.airline.service.domain.dto.get.AirlineDto;
import com.acroteq.ticketing.airline.service.presentation.model.Airline;
import com.acroteq.ticketing.application.mapper.AirlineIdMapper;
import org.mapstruct.Mapper;

@Mapper(uses = { AirlineIdMapper.class, FlightDtoToApiMapper.class })
public interface AirlineDtoToApiMapper {

  Airline convertDtoToApi(AirlineDto dto);
}
