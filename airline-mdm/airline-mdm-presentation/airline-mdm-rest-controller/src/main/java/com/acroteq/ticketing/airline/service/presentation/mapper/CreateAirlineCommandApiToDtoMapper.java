package com.acroteq.ticketing.airline.service.presentation.mapper;

import com.acroteq.ticketing.airline.service.domain.dto.create.CreateAirlineCommandDto;
import com.acroteq.ticketing.airline.service.presentation.model.CreateAirlineCommand;
import org.mapstruct.Mapper;

@Mapper(uses = CreateFlightCommandApiToDtoMapper.class)
public interface CreateAirlineCommandApiToDtoMapper {

  CreateAirlineCommandDto convertApiToDto(CreateAirlineCommand createAirlineCommand);
}
