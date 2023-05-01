package com.acroteq.ticketing.airline.service.presentation.mapper;

import com.acroteq.ticketing.airline.service.domain.dto.create.CreateAirlineResponseDto;
import com.acroteq.ticketing.airline.service.presentation.model.CreateAirlineResponse;
import com.acroteq.ticketing.common.application.mapper.DtoToApiMapper;
import org.mapstruct.Mapper;

@Mapper
public interface CreateAirlineResponseDtoToApiMapper
    extends DtoToApiMapper<CreateAirlineResponseDto, CreateAirlineResponse> {

  @Override
  CreateAirlineResponse convertDtoToApi(CreateAirlineResponseDto responseDto);
}
