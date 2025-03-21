package com.acroteq.ticketing.customer.service.presentation.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.CurrencyIdMapper;
import com.acroteq.application.mapper.id.CustomerIdMapper;
import com.acroteq.common.application.mapper.DtoToApiMapper;
import com.acroteq.ticketing.customer.service.domain.dto.get.CustomerDto;
import com.acroteq.ticketing.customer.service.presentation.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfig.class,
        uses = { CustomerIdMapper.class, CurrencyIdMapper.class, AuditDtoToApiMapper.class })
public interface CustomerDtoToApiMapper extends DtoToApiMapper<CustomerDto, Customer> {

  @Mapping(target = "creditLimitCurrencyId", source = "creditLimit.currencyId")
  @Mapping(target = "creditLimitAmount", source = "creditLimit.amount")
  @Override
  Customer convertDtoToApi(CustomerDto dto);
}
