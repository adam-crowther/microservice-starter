package com.acroteq.ticketing.customer.service.domain.mapper;

import com.acroteq.application.mapper.EventToDtoMapper;
import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.CustomerIdMapper;
import com.acroteq.ticketing.customer.service.domain.dto.create.CreateCustomerResponseDto;
import com.acroteq.ticketing.customer.service.domain.event.CustomerEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfig.class, uses = { CustomerIdMapper.class })
public interface CustomerCreatedEventToResponseDtoMapper
    extends EventToDtoMapper<CustomerEvent, CreateCustomerResponseDto> {


  @Mapping(target = "id", source = "customer.id")
  @Mapping(target = "version", source = "customer.version")
  @Mapping(target = "audit", source = "customer.audit")
  @Mapping(target = "message", constant = "")
  @Override
  CreateCustomerResponseDto convertEventToDto(CustomerEvent event);
}
