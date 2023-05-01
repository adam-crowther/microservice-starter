package com.acroteq.ticketing.airline.service.presentation.mapper;

import com.acroteq.ticketing.airline.service.domain.dto.create.CreateAirlineCommandDto;
import com.acroteq.ticketing.airline.service.presentation.model.CreateAirlineCommand;
import com.acroteq.ticketing.common.application.mapper.ApiToDtoMapper;
import org.mapstruct.Mapper;

@Mapper(uses = CreateFlightCommandApiToDtoMapper.class)
public interface CreateAirlineCommandApiToDtoMapper
    extends ApiToDtoMapper<CreateAirlineCommand, CreateAirlineCommandDto> {

  @Override
  CreateAirlineCommandDto convertApiToDto(CreateAirlineCommand createAirlineCommand);
}
