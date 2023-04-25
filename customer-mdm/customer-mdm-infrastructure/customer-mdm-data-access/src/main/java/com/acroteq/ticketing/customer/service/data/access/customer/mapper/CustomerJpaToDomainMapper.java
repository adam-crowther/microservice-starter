package com.acroteq.ticketing.customer.service.data.access.customer.mapper;

import com.acroteq.ticketing.application.mapper.CurrencyIdMapper;
import com.acroteq.ticketing.application.mapper.CustomerIdMapper;
import com.acroteq.ticketing.customer.service.data.access.customer.entity.CustomerJpaEntity;
import com.acroteq.ticketing.customer.service.domain.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { CustomerIdMapper.class, CurrencyIdMapper.class })
public interface CustomerJpaToDomainMapper {

  @Mapping(target = "creditLimit.currencyId", source = "creditLimitCurrencyId")
  @Mapping(target = "creditLimit.amount", source = "creditLimitAmount")
  Customer convertJpaToDomain(CustomerJpaEntity customerJpaEntity);
}
