package com.acroteq.ticketing.customer.service.presentation.mapper;

import com.acroteq.ticketing.application.mapper.CurrencyIdMapper;
import com.acroteq.ticketing.application.mapper.CustomerIdMapper;
import com.acroteq.ticketing.customer.service.domain.dto.get.CustomerDto;
import com.acroteq.ticketing.customer.service.presentation.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { CustomerIdMapper.class, CurrencyIdMapper.class })
public interface CustomerDtoToApiMapper {

  @Mapping(target = "creditLimitCurrencyId", source = "creditLimit.currencyId")
  @Mapping(target = "creditLimitAmount", source = "creditLimit.amount")
  Customer convertDtoToApi(CustomerDto dto);
}
