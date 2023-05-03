package com.acroteq.ticketing.customer.service.domain.mapper;

import com.acroteq.ticketing.application.mapper.DateTimeMapper;
import com.acroteq.ticketing.application.mapper.DtoToDomainMapper;
import com.acroteq.ticketing.application.mapper.id.CustomerIdMapper;
import com.acroteq.ticketing.customer.service.domain.dto.create.CreateCustomerCommandDto;
import com.acroteq.ticketing.customer.service.domain.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { CustomerIdMapper.class, DateTimeMapper.class })
public interface CreateCustomerDtoToDomainMapper extends DtoToDomainMapper<CreateCustomerCommandDto, Customer> {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "version", ignore = true)
  @Mapping(target = "audit", ignore = true)
  @Override
  Customer convertDtoToDomain(CreateCustomerCommandDto createCustomerCommandDto);
}
