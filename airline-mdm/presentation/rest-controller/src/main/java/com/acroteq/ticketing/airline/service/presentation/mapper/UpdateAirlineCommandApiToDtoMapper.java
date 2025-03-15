package com.acroteq.ticketing.airline.service.presentation.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.common.application.mapper.ApiToDtoMapper;
import com.acroteq.ticketing.airline.service.domain.dto.update.UpdateAirlineCommandDto;
import com.acroteq.ticketing.airline.service.presentation.model.UpdateAirlineCommand;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class, uses = { UpdateFlightCommandApiToDtoMapper.class })
public interface UpdateAirlineCommandApiToDtoMapper
    extends ApiToDtoMapper<UpdateAirlineCommand, UpdateAirlineCommandDto> {

  @Override
  UpdateAirlineCommandDto convertApiToDto(UpdateAirlineCommand updateAirlineCommand);
}
