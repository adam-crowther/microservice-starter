package com.acroteq.ticketing.customer.service.domain.mapper;

import com.acroteq.ticketing.application.mapper.CustomerIdMapper;
import com.acroteq.ticketing.customer.service.domain.dto.get.CustomerDto;
import com.acroteq.ticketing.customer.service.domain.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(uses = { CustomerIdMapper.class })
public interface CustomerDomainToDtoMapper {

  CustomerDto convertDomainToDto(Customer customer);
}
