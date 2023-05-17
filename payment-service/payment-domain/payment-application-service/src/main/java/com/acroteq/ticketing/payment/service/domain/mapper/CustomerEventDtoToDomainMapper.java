package com.acroteq.ticketing.payment.service.domain.mapper;

import com.acroteq.ticketing.application.mapper.DtoToDomainMapper;
import com.acroteq.ticketing.application.mapper.id.CurrencyIdMapper;
import com.acroteq.ticketing.application.mapper.id.CustomerIdMapper;
import com.acroteq.ticketing.payment.service.domain.dto.customer.CustomerEventDto;
import com.acroteq.ticketing.payment.service.domain.entity.Customer;
import com.acroteq.ticketing.payment.service.domain.resolver.CustomerResolver;
import org.mapstruct.Mapper;

@Mapper(uses = { CurrencyIdMapper.class, CustomerIdMapper.class, CustomerResolver.class })
public interface CustomerEventDtoToDomainMapper extends DtoToDomainMapper<CustomerEventDto, Customer> {

  @Override
  Customer convertDtoToDomain(CustomerEventDto customerEventDto);
}
