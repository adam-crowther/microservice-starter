package com.acroteq.food.ordering.system.payment.service.data.access.credithistory.mapper;

import com.acroteq.food.ordering.system.common.application.mapper.IdMapper;
import com.acroteq.food.ordering.system.payment.service.data.access.credithistory.entity.CreditHistoryEntity;
import com.acroteq.food.ordering.system.payment.service.domain.entity.CreditHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { CreditHistoryIdMapper.class, IdMapper.class })
public interface CreditHistoryDomainToEntityMapper {

  @Mapping(target = "creditCurrencyId", source = "credit.currencyId")
  @Mapping(target = "creditAmount", source = "credit.amount")
  @Mapping(target = "type", source = "transactionType")
  CreditHistoryEntity convertDomainToEntity(CreditHistory creditHistory);
}
