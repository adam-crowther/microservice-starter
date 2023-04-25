package com.acroteq.ticketing.airline.service.presentation.mapper;

import com.acroteq.ticketing.airline.service.domain.dto.update.UpdateAirlineCommandDto;
import com.acroteq.ticketing.airline.service.presentation.model.UpdateAirlineCommand;
import org.mapstruct.Mapper;

@Mapper(uses = UpdateFlightCommandApiToDtoMapper.class)
public interface UpdateAirlineCommandApiToDtoMapper {

  UpdateAirlineCommandDto convertApiToDto(UpdateAirlineCommand updateAirlineCommand);
}
