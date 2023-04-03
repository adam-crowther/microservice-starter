package com.acroteq.food.ordering.system.customer.service.application.mapper;

import com.acroteq.food.ordering.system.customer.service.application.model.CreateCustomerResponse;
import com.acroteq.food.ordering.system.customer.service.domain.dto.create.CreateCustomerResponseDto;
import org.mapstruct.Mapper;

@Mapper
public interface CreateCustomerResponseDtoToApiMapper {

  CreateCustomerResponse convertDtoToApi(CreateCustomerResponseDto responseDto);
}
