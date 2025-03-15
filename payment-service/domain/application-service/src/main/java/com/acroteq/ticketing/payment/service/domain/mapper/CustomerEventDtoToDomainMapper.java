package com.acroteq.ticketing.payment.service.domain.mapper;

import com.acroteq.application.mapper.DtoToDomainMapper;
import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.CurrencyIdMapper;
import com.acroteq.application.mapper.id.CustomerIdMapper;
import com.acroteq.ticketing.payment.service.domain.dto.customer.CustomerEventDto;
import com.acroteq.ticketing.payment.service.domain.entity.Customer;
import com.acroteq.ticketing.payment.service.domain.resolver.CustomerResolver;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class,
        uses = { CurrencyIdMapper.class, CustomerIdMapper.class, CustomerResolver.class })
public interface CustomerEventDtoToDomainMapper extends DtoToDomainMapper<CustomerEventDto, Customer> {

  @Override
  Customer convertDtoToDomain(CustomerEventDto customerEventDto);
}
