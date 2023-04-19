package com.acroteq.ticketing.payment.service.data.access.credithistory.mapper;

import com.acroteq.ticketing.application.mapper.CurrencyIdMapper;
import com.acroteq.ticketing.application.mapper.CustomerIdMapper;
import com.acroteq.ticketing.payment.service.data.access.credithistory.entity.CreditHistoryJpaEntity;
import com.acroteq.ticketing.payment.service.domain.entity.CreditHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(uses = { CreditHistoryIdMapper.class, CurrencyIdMapper.class, CustomerIdMapper.class })
public interface CreditHistoryJpaToDomainMapper {

  @Mapping(target = "credit.currencyId", source = "creditCurrencyId")
  @Mapping(target = "credit.amount", source = "creditAmount")
  @Mapping(target = "transactionType", source = "type")
  CreditHistory convertEntityToDomain(CreditHistoryJpaEntity creditHistoryJpaEntity);

  List<CreditHistory> convertEntityToDomain(List<CreditHistoryJpaEntity> creditHistoryJpaEntity);
}
