package com.acroteq.ticketing.airline.service.presentation.mapper;

import com.acroteq.ticketing.airline.service.domain.dto.get.AirlineDto;
import com.acroteq.ticketing.airline.service.presentation.model.Airline;
import com.acroteq.ticketing.application.mapper.MapstructConfig;
import com.acroteq.ticketing.application.mapper.id.AirlineIdMapper;
import com.acroteq.ticketing.common.application.mapper.DtoToApiMapper;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class,
        uses = { AirlineIdMapper.class, FlightDtoToApiMapper.class, AuditDtoToApiMapper.class })
public interface AirlineDtoToApiMapper extends DtoToApiMapper<AirlineDto, Airline> {

  @Override
  Airline convertDtoToApi(AirlineDto dto);
}
