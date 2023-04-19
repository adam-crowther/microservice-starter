package com.acroteq.ticketing.customer.service.application.mapper;

import com.acroteq.ticketing.customer.service.application.model.CreateCustomerResponse;
import com.acroteq.ticketing.customer.service.domain.dto.create.CreateCustomerResponseDto;
import org.mapstruct.Mapper;

@Mapper
public interface CreateCustomerResponseDtoToApiMapper {

  CreateCustomerResponse convertDtoToApi(CreateCustomerResponseDto responseDto);
}
