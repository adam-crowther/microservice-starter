package com.acroteq.ticketing.order.service.domain.mapper.customer;

import com.acroteq.ticketing.application.mapper.id.CustomerIdMapper;
import com.acroteq.ticketing.application.mapper.DtoToDomainMapper;
import com.acroteq.ticketing.order.service.domain.dto.customer.CustomerCreatedDto;
import com.acroteq.ticketing.order.service.domain.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(uses = CustomerIdMapper.class)
public interface CustomerCreatedDtoToDomainMapper extends DtoToDomainMapper<CustomerCreatedDto, Customer> {

  @Override
  Customer convertDtoToDomain(CustomerCreatedDto customer);
}
