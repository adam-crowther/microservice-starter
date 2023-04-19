package com.acroteq.ticketing.customer.service.domain.mapper;

import com.acroteq.ticketing.application.mapper.CustomerIdMapper;
import com.acroteq.ticketing.application.mapper.DateTimeMapper;
import com.acroteq.ticketing.customer.service.domain.dto.create.CreateCustomerCommandDto;
import com.acroteq.ticketing.customer.service.domain.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { CustomerIdMapper.class, DateTimeMapper.class })
public interface CreateCustomerDtoToDomainMapper {

  @Mapping(target = "id", ignore = true)
  Customer convertDtoToDomain(CreateCustomerCommandDto createCustomerCommandDto);
}
