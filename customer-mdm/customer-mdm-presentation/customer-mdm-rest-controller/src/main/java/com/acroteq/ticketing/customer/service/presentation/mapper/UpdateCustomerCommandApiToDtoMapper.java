package com.acroteq.ticketing.customer.service.presentation.mapper;

import com.acroteq.ticketing.application.mapper.CurrencyIdMapper;
import com.acroteq.ticketing.customer.service.domain.dto.update.UpdateCustomerCommandDto;
import com.acroteq.ticketing.customer.service.presentation.model.UpdateCustomerCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = CurrencyIdMapper.class)
public interface UpdateCustomerCommandApiToDtoMapper {

  @Mapping(target = "creditLimit.currencyId", source = "creditLimitCurrencyId")
  @Mapping(target = "creditLimit.amount", source = "creditLimitAmount")
  UpdateCustomerCommandDto convertApiToDto(UpdateCustomerCommand updateCustomerCommand);
}
