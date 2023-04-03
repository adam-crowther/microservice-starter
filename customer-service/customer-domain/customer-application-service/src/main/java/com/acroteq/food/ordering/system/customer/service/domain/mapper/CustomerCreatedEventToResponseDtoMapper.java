package com.acroteq.food.ordering.system.customer.service.domain.mapper;

import com.acroteq.food.ordering.system.common.application.mapper.IdMapper;
import com.acroteq.food.ordering.system.customer.service.domain.dto.create.CreateCustomerResponseDto;
import com.acroteq.food.ordering.system.customer.service.domain.event.CustomerEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { IdMapper.class })
public interface CustomerCreatedEventToResponseDtoMapper {

  @Mapping(target = "message", constant = "")
  CreateCustomerResponseDto convertCreatedEventToResponseDto(CustomerEvent event);
}
