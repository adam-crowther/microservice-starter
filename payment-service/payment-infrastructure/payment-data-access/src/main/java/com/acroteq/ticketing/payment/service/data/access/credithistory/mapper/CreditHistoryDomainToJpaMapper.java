package com.acroteq.ticketing.payment.service.data.access.credithistory.mapper;

import com.acroteq.ticketing.application.mapper.CurrencyIdMapper;
import com.acroteq.ticketing.application.mapper.CustomerIdMapper;
import com.acroteq.ticketing.payment.service.data.access.credithistory.entity.CreditHistoryJpaEntity;
import com.acroteq.ticketing.payment.service.domain.entity.CreditHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { CreditHistoryIdMapper.class, CurrencyIdMapper.class, CustomerIdMapper.class })
public interface CreditHistoryDomainToJpaMapper {

  @Mapping(target = "creditCurrencyId", source = "credit.currencyId")
  @Mapping(target = "creditAmount", source = "credit.amount")
  CreditHistoryJpaEntity convertDomainToJpa(CreditHistory creditHistory);
}
