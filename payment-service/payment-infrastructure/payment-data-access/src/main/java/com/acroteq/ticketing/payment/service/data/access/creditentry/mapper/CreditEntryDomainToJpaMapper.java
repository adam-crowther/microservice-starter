package com.acroteq.ticketing.payment.service.data.access.creditentry.mapper;

import com.acroteq.ticketing.application.mapper.id.CurrencyIdMapper;
import com.acroteq.ticketing.application.mapper.id.CustomerIdMapper;
import com.acroteq.ticketing.infrastructure.mapper.DomainToJpaMapper;
import com.acroteq.ticketing.payment.service.data.access.creditentry.entity.CreditEntryJpaEntity;
import com.acroteq.ticketing.payment.service.data.access.customer.resolver.CustomerJpaResolver;
import com.acroteq.ticketing.payment.service.domain.entity.CreditEntry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(uses = { CurrencyIdMapper.class, CustomerIdMapper.class, CreditEntryIdMapper.class, CustomerJpaResolver.class })
public interface CreditEntryDomainToJpaMapper extends DomainToJpaMapper<CreditEntry, CreditEntryJpaEntity> {

  @Mapping(target = "audit", ignore = true)
  @Mapping(target = "totalCreditCurrencyId", source = "totalCredit.currencyId")
  @Mapping(target = "totalCreditAmount", source = "totalCredit.amount")
  @Mapping(target = "customer", source = "customer.id")
  @Override
  CreditEntryJpaEntity convertDomainToJpa(CreditEntry entity);

  @Mapping(target = "audit", ignore = true)
  @Mapping(target = "totalCreditCurrencyId", source = "totalCredit.currencyId")
  @Mapping(target = "totalCreditAmount", source = "totalCredit.amount")
  @Mapping(target = "customer", source = "customer.id")
  @Override
  CreditEntryJpaEntity convertDomainToJpa(CreditEntry entity, @MappingTarget CreditEntryJpaEntity jpaEntity);
}
