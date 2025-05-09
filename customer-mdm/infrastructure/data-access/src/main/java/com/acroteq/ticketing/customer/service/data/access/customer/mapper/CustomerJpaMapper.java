package com.acroteq.ticketing.customer.service.data.access.customer.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.CurrencyIdMapper;
import com.acroteq.application.mapper.id.CustomerIdMapper;
import com.acroteq.infrastructure.mapper.JpaMapper;
import com.acroteq.ticketing.customer.service.data.access.customer.entity.CustomerJpaEntity;
import com.acroteq.ticketing.customer.service.data.access.customer.repository.CustomerJpaRepository;
import com.acroteq.ticketing.customer.service.domain.entity.Customer;
import lombok.Getter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(config = MapstructConfig.class, uses = { CustomerIdMapper.class, CurrencyIdMapper.class })
public abstract class CustomerJpaMapper implements JpaMapper<Customer, CustomerJpaEntity> {

  @Getter
  @Autowired
  private CustomerJpaRepository repository;

  @Mapping(target = "creditLimit.currencyId", source = "creditLimitCurrencyId")
  @Mapping(target = "creditLimit.amount", source = "creditLimitAmount")
  @Override
  public abstract Customer convert(CustomerJpaEntity customerJpaEntity);

  @Mapping(target = "audit", ignore = true)
  @Mapping(target = "creditLimitCurrencyId", source = "creditLimit.currencyId")
  @Mapping(target = "creditLimitAmount", source = "creditLimit.amount")
  @Override
  public abstract CustomerJpaEntity convertNew(Customer entity);

  @Mapping(target = "audit", ignore = true)
  @Mapping(target = "creditLimitCurrencyId", source = "creditLimit.currencyId")
  @Mapping(target = "creditLimitAmount", source = "creditLimit.amount")
  @Override
  public abstract CustomerJpaEntity convertExisting(Customer entity, @MappingTarget CustomerJpaEntity jpaEntity);
}
