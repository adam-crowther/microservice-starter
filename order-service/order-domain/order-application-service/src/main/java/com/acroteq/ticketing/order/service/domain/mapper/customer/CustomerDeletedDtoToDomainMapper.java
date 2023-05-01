package com.acroteq.ticketing.order.service.domain.mapper.customer;

import com.acroteq.ticketing.application.mapper.id.CustomerIdMapper;
import com.acroteq.ticketing.application.mapper.DtoToDomainMapper;
import com.acroteq.ticketing.domain.valueobject.CustomerId;
import com.acroteq.ticketing.order.service.domain.dto.customer.CustomerDeletedDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = CustomerIdMapper.class)
public interface CustomerDeletedDtoToDomainMapper extends DtoToDomainMapper<CustomerDeletedDto, CustomerId> {

  @Mapping(target = "value", source = "id")
  @Override
  CustomerId convertDtoToDomain(CustomerDeletedDto dto);
}
