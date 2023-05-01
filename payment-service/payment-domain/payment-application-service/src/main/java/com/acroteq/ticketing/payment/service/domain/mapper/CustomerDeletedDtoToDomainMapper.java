package com.acroteq.ticketing.payment.service.domain.mapper;

import com.acroteq.ticketing.application.mapper.DtoToDomainMapper;
import com.acroteq.ticketing.application.mapper.id.CurrencyIdMapper;
import com.acroteq.ticketing.application.mapper.id.CustomerIdMapper;
import com.acroteq.ticketing.domain.valueobject.CustomerId;
import com.acroteq.ticketing.payment.service.domain.dto.customer.CustomerDeletedDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { CurrencyIdMapper.class, CustomerIdMapper.class })
public interface CustomerDeletedDtoToDomainMapper extends DtoToDomainMapper<CustomerDeletedDto, CustomerId> {

  @Mapping(target = "value", source = "id")
  @Override
  CustomerId convertDtoToDomain(CustomerDeletedDto customerDeletedDto);
}
