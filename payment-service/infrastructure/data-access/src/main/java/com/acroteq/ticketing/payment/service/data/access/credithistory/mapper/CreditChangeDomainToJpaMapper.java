package com.acroteq.ticketing.payment.service.data.access.credithistory.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.CurrencyIdMapper;
import com.acroteq.application.mapper.id.CustomerIdMapper;
import com.acroteq.infrastructure.mapper.DomainToJpaMapper;
import com.acroteq.ticketing.payment.service.data.access.credithistory.entity.CreditChangeJpaEntity;
import com.acroteq.ticketing.payment.service.data.access.customer.resolver.CustomerJpaResolver;
import com.acroteq.ticketing.payment.service.domain.entity.CreditChange;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapstructConfig.class,
        uses = { CreditChangeIdMapper.class,
                 CurrencyIdMapper.class,
                 CustomerIdMapper.class,
                 CustomerJpaResolver.class })
public interface CreditChangeDomainToJpaMapper extends DomainToJpaMapper<CreditChange, CreditChangeJpaEntity> {

  @Mapping(target = "audit", ignore = true)
  @Mapping(target = "creditDeltaCurrencyId", source = "creditDelta.currencyId")
  @Mapping(target = "creditDeltaAmount", source = "creditDelta.amount")
  @Mapping(target = "customer", source = "customer.id")
  @Override
  CreditChangeJpaEntity convertDomainToJpa(CreditChange entity);

  @Mapping(target = "audit", ignore = true)
  @Mapping(target = "creditDeltaCurrencyId", source = "creditDelta.currencyId")
  @Mapping(target = "creditDeltaAmount", source = "creditDelta.amount")
  @Mapping(target = "customer", source = "customer.id")
  @Override
  CreditChangeJpaEntity convertDomainToJpa(CreditChange entity, @MappingTarget CreditChangeJpaEntity jpaEntity);
}
