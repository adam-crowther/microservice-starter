package com.acroteq.ticketing.payment.service.data.access.credithistory.mapper;

import com.acroteq.ticketing.application.mapper.id.CurrencyIdMapper;
import com.acroteq.ticketing.application.mapper.id.CustomerIdMapper;
import com.acroteq.ticketing.infrastructure.mapper.DomainToJpaMapper;
import com.acroteq.ticketing.payment.service.data.access.credithistory.entity.CreditHistoryJpaEntity;
import com.acroteq.ticketing.payment.service.domain.entity.CreditHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(uses = { CreditHistoryIdMapper.class, CurrencyIdMapper.class, CustomerIdMapper.class })
public interface CreditHistoryDomainToJpaMapper extends DomainToJpaMapper<CreditHistory, CreditHistoryJpaEntity> {

  @Mapping(target = "audit", ignore = true)
  @Mapping(target = "creditCurrencyId", source = "credit.currencyId")
  @Mapping(target = "creditAmount", source = "credit.amount")
  @Override
  CreditHistoryJpaEntity convertDomainToJpa(CreditHistory entity);

  @Mapping(target = "audit", ignore = true)
  @Mapping(target = "creditCurrencyId", source = "credit.currencyId")
  @Mapping(target = "creditAmount", source = "credit.amount")
  @Override
  CreditHistoryJpaEntity convertDomainToJpa(CreditHistory entity, @MappingTarget CreditHistoryJpaEntity jpaEntity);
}
