package com.acroteq.ticketing.airline.service.domain.mapper;

import com.acroteq.application.mapper.EventToDtoMapper;
import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.AirlineIdMapper;
import com.acroteq.ticketing.airline.service.domain.dto.create.CreateAirlineResponseDto;
import com.acroteq.ticketing.airline.service.domain.event.AirlineEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfig.class, uses = { AirlineIdMapper.class })
public interface AirlineCreatedEventToResponseDtoMapper
    extends EventToDtoMapper<AirlineEvent, CreateAirlineResponseDto> {

  @Mapping(target = "message", constant = "")
  @Mapping(target = "id", source = "airline.id")
  @Mapping(target = "version", source = "airline.version")
  @Mapping(target = "audit", source = "airline.audit")
  @Override
  CreateAirlineResponseDto convertEventToDto(AirlineEvent event);
}
