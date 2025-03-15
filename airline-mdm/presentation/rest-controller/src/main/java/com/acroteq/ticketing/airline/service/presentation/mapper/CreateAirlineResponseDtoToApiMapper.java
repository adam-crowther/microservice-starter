package com.acroteq.ticketing.airline.service.presentation.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.common.application.mapper.DtoToApiMapper;
import com.acroteq.ticketing.airline.service.domain.dto.create.CreateAirlineResponseDto;
import com.acroteq.ticketing.airline.service.presentation.model.CreateAirlineResponse;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class, uses = AuditDtoToApiMapper.class)
public interface CreateAirlineResponseDtoToApiMapper
    extends DtoToApiMapper<CreateAirlineResponseDto, CreateAirlineResponse> {

  @Override
  CreateAirlineResponse convertDtoToApi(CreateAirlineResponseDto responseDto);
}
