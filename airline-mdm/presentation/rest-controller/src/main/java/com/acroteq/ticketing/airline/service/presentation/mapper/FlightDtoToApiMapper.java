package com.acroteq.ticketing.airline.service.presentation.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.AirlineIdMapper;
import com.acroteq.common.application.mapper.DtoToApiMapper;
import com.acroteq.ticketing.airline.service.domain.dto.get.FlightDto;
import com.acroteq.ticketing.airline.service.presentation.model.Flight;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfig.class, uses = { AirlineIdMapper.class, CashValueDtoToApiMapper.class })
public interface FlightDtoToApiMapper extends DtoToApiMapper<FlightDto, Flight> {

  @Override
  Flight convertDtoToApi(FlightDto dto);

  List<Flight> convertDtoToApi(List<FlightDto> dto);
}
