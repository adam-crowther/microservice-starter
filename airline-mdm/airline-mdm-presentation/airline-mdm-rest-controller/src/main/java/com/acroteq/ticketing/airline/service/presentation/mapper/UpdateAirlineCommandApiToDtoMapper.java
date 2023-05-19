package com.acroteq.ticketing.airline.service.presentation.mapper;

import com.acroteq.ticketing.airline.service.domain.dto.update.UpdateAirlineCommandDto;
import com.acroteq.ticketing.airline.service.presentation.model.UpdateAirlineCommand;
import com.acroteq.ticketing.application.mapper.MapstructConfig;
import com.acroteq.ticketing.common.application.mapper.ApiToDtoMapper;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class, uses = { UpdateFlightCommandApiToDtoMapper.class })
public interface UpdateAirlineCommandApiToDtoMapper
    extends ApiToDtoMapper<UpdateAirlineCommand, UpdateAirlineCommandDto> {

  @Override
  UpdateAirlineCommandDto convertApiToDto(UpdateAirlineCommand updateAirlineCommand);
}
