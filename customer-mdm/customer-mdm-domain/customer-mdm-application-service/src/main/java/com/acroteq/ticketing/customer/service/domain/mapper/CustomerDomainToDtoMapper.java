package com.acroteq.ticketing.customer.service.domain.mapper;

import com.acroteq.ticketing.application.mapper.DomainToDtoMapper;
import com.acroteq.ticketing.application.mapper.MapstructConfig;
import com.acroteq.ticketing.application.mapper.id.CustomerIdMapper;
import com.acroteq.ticketing.customer.service.domain.dto.get.CustomerDto;
import com.acroteq.ticketing.customer.service.domain.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class, uses = { CustomerIdMapper.class })
public interface CustomerDomainToDtoMapper extends DomainToDtoMapper<Customer, CustomerDto> {

  @Override
  CustomerDto convertDomainToDto(Customer customer);
}
