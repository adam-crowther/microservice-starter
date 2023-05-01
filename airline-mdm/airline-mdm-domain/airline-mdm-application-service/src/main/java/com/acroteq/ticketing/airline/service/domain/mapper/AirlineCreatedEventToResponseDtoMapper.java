package com.acroteq.ticketing.airline.service.domain.mapper;

import com.acroteq.ticketing.airline.service.domain.dto.create.CreateAirlineResponseDto;
import com.acroteq.ticketing.airline.service.domain.event.AirlineEvent;
import com.acroteq.ticketing.application.mapper.id.AirlineIdMapper;
import com.acroteq.ticketing.application.mapper.EventToDtoMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { AirlineIdMapper.class })
public interface AirlineCreatedEventToResponseDtoMapper
    extends EventToDtoMapper<AirlineEvent, CreateAirlineResponseDto> {

  @Mapping(target = "message", constant = "")
  @Override
  CreateAirlineResponseDto convertEventToDto(AirlineEvent event);
}
