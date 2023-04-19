package com.acroteq.ticketing.customer.service.domain.mapper;

import com.acroteq.ticketing.application.mapper.CustomerIdMapper;
import com.acroteq.ticketing.customer.service.domain.dto.create.CreateCustomerResponseDto;
import com.acroteq.ticketing.customer.service.domain.event.CustomerEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { CustomerIdMapper.class })
public interface CustomerCreatedEventToResponseDtoMapper {

  @Mapping(target = "message", constant = "")
  CreateCustomerResponseDto convertCreatedEventToResponseDto(CustomerEvent event);
}
