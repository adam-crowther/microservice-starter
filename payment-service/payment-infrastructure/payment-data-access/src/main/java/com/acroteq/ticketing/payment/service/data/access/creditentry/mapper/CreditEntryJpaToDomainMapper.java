package com.acroteq.ticketing.payment.service.data.access.creditentry.mapper;

import com.acroteq.ticketing.application.mapper.CurrencyIdMapper;
import com.acroteq.ticketing.application.mapper.CustomerIdMapper;
import com.acroteq.ticketing.payment.service.data.access.creditentry.entity.CreditEntryJpaEntity;
import com.acroteq.ticketing.payment.service.domain.entity.CreditEntry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { CurrencyIdMapper.class, CustomerIdMapper.class })
public interface CreditEntryJpaToDomainMapper {


  @Mapping(target = "id", source = "customerId")
  @Mapping(target = "totalCredit.currencyId", source = "totalCreditCurrencyId")
  @Mapping(target = "totalCredit.amount", source = "totalCreditAmount")
  CreditEntry convertJpaToDomain(CreditEntryJpaEntity creditEntryJpaEntity);
}
