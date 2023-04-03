package com.acroteq.food.ordering.system.payment.service.data.access.creditentry.mapper;

import com.acroteq.food.ordering.system.common.application.mapper.IdMapper;
import com.acroteq.food.ordering.system.payment.service.data.access.creditentry.entity.CreditEntryEntity;
import com.acroteq.food.ordering.system.payment.service.domain.entity.CreditEntry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { CreditEntryIdMapper.class, IdMapper.class })
public interface CreditEntryEntityToDomainMapper {


  @Mapping(target = "totalCredit.currencyId", source = "totalCreditCurrencyId")
  @Mapping(target = "totalCredit.amount", source = "totalCreditAmount")
  CreditEntry convertEntityToDomain(CreditEntryEntity creditEntryEntity);
}
