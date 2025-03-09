package com.acroteq.ticketing.customer.service.presentation.mapper;

import com.acroteq.ticketing.application.mapper.MapstructConfig;
import com.acroteq.ticketing.application.mapper.id.CurrencyIdMapper;
import com.acroteq.ticketing.common.application.mapper.ApiToDtoMapper;
import com.acroteq.ticketing.customer.service.domain.dto.create.CreateCustomerCommandDto;
import com.acroteq.ticketing.customer.service.presentation.model.CreateCustomerCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfig.class, uses = CurrencyIdMapper.class)
public interface CreateCustomerCommandApiToDtoMapper
    extends ApiToDtoMapper<CreateCustomerCommand, CreateCustomerCommandDto> {

  @Mapping(target = "creditLimit.currencyId", source = "creditLimitCurrencyId")
  @Mapping(target = "creditLimit.amount", source = "creditLimitAmount")
  @Override
  CreateCustomerCommandDto convertApiToDto(CreateCustomerCommand createCustomerCommand);
}
