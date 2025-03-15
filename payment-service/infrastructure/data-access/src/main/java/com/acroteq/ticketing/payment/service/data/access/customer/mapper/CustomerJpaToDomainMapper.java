package com.acroteq.ticketing.payment.service.data.access.customer.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.CurrencyIdMapper;
import com.acroteq.application.mapper.id.CustomerIdMapper;
import com.acroteq.infrastructure.mapper.JpaToDomainMapper;
import com.acroteq.ticketing.payment.service.data.access.customer.entity.CustomerJpaEntity;
import com.acroteq.ticketing.payment.service.domain.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfig.class, uses = { CustomerIdMapper.class, CurrencyIdMapper.class })
public interface CustomerJpaToDomainMapper extends JpaToDomainMapper<CustomerJpaEntity, Customer> {

  @Mapping(target = "creditLimit.currencyId", source = "creditLimitCurrencyId")
  @Mapping(target = "creditLimit.amount", source = "creditLimitAmount")
  @Override
  Customer convertJpaToDomain(CustomerJpaEntity entity);

  List<Customer> convertJpaToDomain(List<CustomerJpaEntity> entity);
}
