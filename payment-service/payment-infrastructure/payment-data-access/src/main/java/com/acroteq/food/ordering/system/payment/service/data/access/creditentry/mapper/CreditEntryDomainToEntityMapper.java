package com.acroteq.food.ordering.system.payment.service.data.access.creditentry.mapper;

import com.acroteq.food.ordering.system.common.application.mapper.IdMapper;
import com.acroteq.food.ordering.system.payment.service.data.access.creditentry.entity.CreditEntryEntity;
import com.acroteq.food.ordering.system.payment.service.domain.entity.CreditEntry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { CreditEntryIdMapper.class, IdMapper.class })
public interface CreditEntryDomainToEntityMapper {

  @Mapping(target = "totalCreditCurrencyId", source = "totalCredit.currencyId")
  @Mapping(target = "totalCreditAmount", source = "totalCredit.amount")
  CreditEntryEntity convertDomainToEntity(CreditEntry creditEntry);
}
