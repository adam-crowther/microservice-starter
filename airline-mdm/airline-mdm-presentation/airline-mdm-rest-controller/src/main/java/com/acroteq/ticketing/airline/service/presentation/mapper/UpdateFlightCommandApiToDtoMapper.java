package com.acroteq.ticketing.airline.service.presentation.mapper;

import com.acroteq.ticketing.airline.service.domain.dto.update.UpdateFlightCommandDto;
import com.acroteq.ticketing.airline.service.presentation.model.UpdateFlightCommand;
import com.acroteq.ticketing.common.application.mapper.ApiToDtoMapper;
import org.mapstruct.Mapper;

@Mapper(uses = CashValueCommandApiToDtoMapper.class)
public interface UpdateFlightCommandApiToDtoMapper extends ApiToDtoMapper<UpdateFlightCommand, UpdateFlightCommandDto> {

  @Override
  UpdateFlightCommandDto convertApiToDto(UpdateFlightCommand updateFlightCommand);
}
