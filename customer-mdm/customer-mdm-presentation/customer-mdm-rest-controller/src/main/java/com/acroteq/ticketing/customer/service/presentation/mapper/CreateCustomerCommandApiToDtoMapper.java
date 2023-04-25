package com.acroteq.ticketing.customer.service.presentation.mapper;

import com.acroteq.ticketing.application.mapper.CurrencyIdMapper;
import com.acroteq.ticketing.customer.service.domain.dto.create.CreateCustomerCommandDto;
import com.acroteq.ticketing.customer.service.presentation.model.CreateCustomerCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = CurrencyIdMapper.class)
public interface CreateCustomerCommandApiToDtoMapper {

  @Mapping(target = "creditLimit.currencyId", source = "creditLimitCurrencyId")
  @Mapping(target = "creditLimit.amount", source = "creditLimitAmount")
  CreateCustomerCommandDto convertApiToDto(CreateCustomerCommand createCustomerCommand);
}
