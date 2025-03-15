package com.acroteq.ticketing.payment.service.data.access.customer.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.CurrencyIdMapper;
import com.acroteq.application.mapper.id.CustomerIdMapper;
import com.acroteq.infrastructure.mapper.DomainToJpaMapper;
import com.acroteq.infrastructure.mapper.EventIdDomainToJpaMapper;
import com.acroteq.ticketing.payment.service.data.access.customer.entity.CustomerJpaEntity;
import com.acroteq.ticketing.payment.service.domain.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapstructConfig.class,
        uses = { CustomerIdMapper.class, CurrencyIdMapper.class, EventIdDomainToJpaMapper.class })
public interface CustomerDomainToJpaMapper extends DomainToJpaMapper<Customer, CustomerJpaEntity> {

  @Mapping(target = "creditLimitCurrencyId", source = "creditLimit.currencyId")
  @Mapping(target = "creditLimitAmount", source = "creditLimit.amount")
  @Override
  CustomerJpaEntity convertDomainToJpa(Customer entity);

  @Mapping(target = "version", source = "entity.version")
  @Mapping(target = "eventId", source = "entity.eventId")
  @Mapping(target = "creditLimitCurrencyId", source = "creditLimit.currencyId")
  @Mapping(target = "creditLimitAmount", source = "creditLimit.amount")
  @Override
  CustomerJpaEntity convertDomainToJpa(Customer entity, @MappingTarget CustomerJpaEntity jpaEntity);
}
