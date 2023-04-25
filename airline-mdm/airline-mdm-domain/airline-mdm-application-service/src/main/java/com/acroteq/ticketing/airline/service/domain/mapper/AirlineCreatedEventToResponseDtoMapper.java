package com.acroteq.ticketing.airline.service.domain.mapper;

import com.acroteq.ticketing.airline.service.domain.dto.create.CreateAirlineResponseDto;
import com.acroteq.ticketing.airline.service.domain.event.AirlineEvent;
import com.acroteq.ticketing.application.mapper.AirlineIdMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { AirlineIdMapper.class })
public interface AirlineCreatedEventToResponseDtoMapper {

  @Mapping(target = "message", constant = "")
  CreateAirlineResponseDto convertCreatedEventToResponseDto(AirlineEvent event);
}
