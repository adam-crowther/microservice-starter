package com.acroteq.ticketing.customer.service.presentation.mapper;

import com.acroteq.ticketing.application.mapper.MapstructConfig;
import com.acroteq.ticketing.common.application.mapper.DtoToApiMapper;
import com.acroteq.ticketing.customer.service.domain.dto.create.CreateCustomerResponseDto;
import com.acroteq.ticketing.customer.service.presentation.model.CreateCustomerResponse;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class, uses = AuditDtoToApiMapper.class)
public interface CreateCustomerResponseDtoToApiMapper
    extends DtoToApiMapper<CreateCustomerResponseDto, CreateCustomerResponse> {

  @Override
  CreateCustomerResponse convertDtoToApi(CreateCustomerResponseDto responseDto);
}
