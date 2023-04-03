package com.acroteq.food.ordering.system.payment.service.data.access.credithistory.mapper;

import com.acroteq.food.ordering.system.common.application.mapper.IdMapper;
import com.acroteq.food.ordering.system.payment.service.data.access.credithistory.entity.CreditHistoryEntity;
import com.acroteq.food.ordering.system.payment.service.domain.entity.CreditHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { CreditHistoryIdMapper.class, IdMapper.class })
public interface CreditHistoryEntityToDomainMapper {

  @Mapping(target = "credit.currencyId", source = "creditCurrencyId")
  @Mapping(target = "credit.amount", source = "creditAmount")
  @Mapping(target = "transactionType", source = "type")
  CreditHistory convertEntityToDomain(CreditHistoryEntity creditHistoryEntity);
}
