package com.acroteq.ticketing.airline.service.presentation.mapper;

import com.acroteq.ticketing.airline.service.domain.dto.create.CreateFlightCommandDto;
import com.acroteq.ticketing.airline.service.presentation.model.CreateFlightCommand;
import org.mapstruct.Mapper;

@Mapper(uses = CashValueCommandApiToDtoMapper.class)
public interface CreateFlightCommandApiToDtoMapper {

  CreateFlightCommandDto convertApiToDto(CreateFlightCommand createFlightCommand);
}
