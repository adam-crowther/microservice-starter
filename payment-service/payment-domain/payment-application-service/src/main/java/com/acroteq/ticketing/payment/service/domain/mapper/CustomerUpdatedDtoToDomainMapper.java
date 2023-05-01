package com.acroteq.ticketing.payment.service.domain.mapper;

import com.acroteq.ticketing.application.mapper.DtoToDomainMapper;
import com.acroteq.ticketing.application.mapper.id.CurrencyIdMapper;
import com.acroteq.ticketing.application.mapper.id.CustomerIdMapper;
import com.acroteq.ticketing.payment.service.domain.dto.customer.CustomerUpdatedDto;
import com.acroteq.ticketing.payment.service.domain.entity.CreditEntry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { CurrencyIdMapper.class, CustomerIdMapper.class })
public interface CustomerUpdatedDtoToDomainMapper extends DtoToDomainMapper<CustomerUpdatedDto, CreditEntry> {

  @Mapping(target = "totalCredit", source = "creditLimit")
  @Override
  CreditEntry convertDtoToDomain(CustomerUpdatedDto customerUpdatedDto);
}
