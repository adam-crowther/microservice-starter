package com.acroteq.ticketing.airline.service.presentation.mapper;

import com.acroteq.ticketing.airline.service.domain.dto.create.CreateAirlineResponseDto;
import com.acroteq.ticketing.airline.service.presentation.model.CreateAirlineResponse;
import org.mapstruct.Mapper;

@Mapper
public interface CreateAirlineResponseDtoToApiMapper {

  CreateAirlineResponse convertDtoToApi(CreateAirlineResponseDto responseDto);
}
