package com.acroteq.ticketing.airline.service.presentation.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.common.application.mapper.ApiToDtoMapper;
import com.acroteq.ticketing.airline.service.domain.dto.create.CreateAirlineCommandDto;
import com.acroteq.ticketing.airline.service.presentation.model.CreateAirlineCommand;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class, uses = CreateFlightCommandApiToDtoMapper.class)
public interface CreateAirlineCommandApiToDtoMapper
    extends ApiToDtoMapper<CreateAirlineCommand, CreateAirlineCommandDto> {

  @Override
  CreateAirlineCommandDto convertApiToDto(CreateAirlineCommand createAirlineCommand);
}
