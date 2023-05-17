package com.acroteq.ticketing.payment.service.data.access.creditbalance.mapper;

import com.acroteq.ticketing.application.mapper.id.CurrencyIdMapper;
import com.acroteq.ticketing.application.mapper.id.CustomerIdMapper;
import com.acroteq.ticketing.infrastructure.mapper.DomainToJpaMapper;
import com.acroteq.ticketing.payment.service.data.access.creditbalance.entity.CreditBalanceJpaEntity;
import com.acroteq.ticketing.payment.service.data.access.customer.resolver.CustomerJpaResolver;
import com.acroteq.ticketing.payment.service.domain.entity.CreditBalance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(uses = { CurrencyIdMapper.class,
                 CustomerIdMapper.class,
                 CreditBalanceIdMapper.class,
                 CustomerJpaResolver.class })
public interface CreditBalanceDomainToJpaMapper extends DomainToJpaMapper<CreditBalance, CreditBalanceJpaEntity> {

  @Mapping(target = "audit", ignore = true)
  @Mapping(target = "totalCreditCurrencyId", source = "totalCredit.currencyId")
  @Mapping(target = "totalCreditAmount", source = "totalCredit.amount")
  @Mapping(target = "customer", source = "customer.id")
  @Override
  CreditBalanceJpaEntity convertDomainToJpa(CreditBalance entity);

  @Mapping(target = "audit", ignore = true)
  @Mapping(target = "totalCreditCurrencyId", source = "totalCredit.currencyId")
  @Mapping(target = "totalCreditAmount", source = "totalCredit.amount")
  @Mapping(target = "customer", source = "customer.id")
  @Override
  CreditBalanceJpaEntity convertDomainToJpa(CreditBalance entity, @MappingTarget CreditBalanceJpaEntity jpaEntity);
}
