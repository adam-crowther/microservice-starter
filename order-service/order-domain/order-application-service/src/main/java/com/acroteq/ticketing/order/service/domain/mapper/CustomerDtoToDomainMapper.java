package com.acroteq.ticketing.order.service.domain.mapper;

import com.acroteq.ticketing.application.mapper.CustomerIdMapper;
import com.acroteq.ticketing.order.service.domain.dto.customer.CustomerDto;
import com.acroteq.ticketing.order.service.domain.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(uses = CustomerIdMapper.class)
public interface CustomerDtoToDomainMapper {

  Customer convertDtoToDomain(CustomerDto customer);
}
