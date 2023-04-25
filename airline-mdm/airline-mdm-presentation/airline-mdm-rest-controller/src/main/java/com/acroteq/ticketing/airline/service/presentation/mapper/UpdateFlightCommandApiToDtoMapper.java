package com.acroteq.ticketing.airline.service.presentation.mapper;

import com.acroteq.ticketing.airline.service.domain.dto.update.UpdateFlightCommandDto;
import com.acroteq.ticketing.airline.service.presentation.model.UpdateFlightCommand;
import org.mapstruct.Mapper;

@Mapper(uses = CashValueCommandApiToDtoMapper.class)
public interface UpdateFlightCommandApiToDtoMapper {

  UpdateFlightCommandDto convertApiToDto(UpdateFlightCommand updateFlightCommand);
}
