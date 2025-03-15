package com.acroteq.ticketing.airline.service.presentation.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.common.application.mapper.ApiToDtoMapper;
import com.acroteq.ticketing.airline.service.domain.dto.update.UpdateFlightCommandDto;
import com.acroteq.ticketing.airline.service.presentation.model.UpdateFlightCommand;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class, uses = CashValueCommandApiToDtoMapper.class)
public interface UpdateFlightCommandApiToDtoMapper extends ApiToDtoMapper<UpdateFlightCommand, UpdateFlightCommandDto> {

  @Override
  UpdateFlightCommandDto convertApiToDto(UpdateFlightCommand updateFlightCommand);
}
