package com.acroteq.ticketing.customer.service.presentation.mapper;

import com.acroteq.ticketing.customer.service.domain.dto.create.CreateCustomerResponseDto;
import com.acroteq.ticketing.customer.service.presentation.model.CreateCustomerResponse;
import org.mapstruct.Mapper;

@Mapper
public interface CreateCustomerResponseDtoToApiMapper {

  CreateCustomerResponse convertDtoToApi(CreateCustomerResponseDto responseDto);
}
