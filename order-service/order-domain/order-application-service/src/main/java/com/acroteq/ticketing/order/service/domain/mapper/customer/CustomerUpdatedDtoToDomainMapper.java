package com.acroteq.ticketing.order.service.domain.mapper.customer;

import com.acroteq.ticketing.application.mapper.id.CustomerIdMapper;
import com.acroteq.ticketing.application.mapper.DtoToDomainMapper;
import com.acroteq.ticketing.order.service.domain.dto.customer.CustomerUpdatedDto;
import com.acroteq.ticketing.order.service.domain.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(uses = CustomerIdMapper.class)
public interface CustomerUpdatedDtoToDomainMapper extends DtoToDomainMapper<CustomerUpdatedDto, Customer> {

  @Override
  Customer convertDtoToDomain(CustomerUpdatedDto customer);
}
