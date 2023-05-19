package com.acroteq.ticketing.airline.service.presentation.mapper;

import com.acroteq.ticketing.airline.service.domain.dto.create.CreateFlightCommandDto;
import com.acroteq.ticketing.airline.service.presentation.model.CreateFlightCommand;
import com.acroteq.ticketing.application.mapper.MapstructConfig;
import com.acroteq.ticketing.common.application.mapper.ApiToDtoMapper;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class, uses = CashValueCommandApiToDtoMapper.class)
public interface CreateFlightCommandApiToDtoMapper extends ApiToDtoMapper<CreateFlightCommand, CreateFlightCommandDto> {

  @Override
  CreateFlightCommandDto convertApiToDto(CreateFlightCommand createFlightCommand);
}
