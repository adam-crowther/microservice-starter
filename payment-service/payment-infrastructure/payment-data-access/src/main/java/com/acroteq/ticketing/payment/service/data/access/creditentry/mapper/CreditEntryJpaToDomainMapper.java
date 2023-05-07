package com.acroteq.ticketing.payment.service.data.access.creditentry.mapper;

import com.acroteq.ticketing.application.mapper.id.CurrencyIdMapper;
import com.acroteq.ticketing.application.mapper.id.CustomerIdMapper;
import com.acroteq.ticketing.infrastructure.mapper.JpaToDomainMapper;
import com.acroteq.ticketing.payment.service.data.access.creditentry.entity.CreditEntryJpaEntity;
import com.acroteq.ticketing.payment.service.data.access.customer.mapper.CustomerJpaToDomainMapper;
import com.acroteq.ticketing.payment.service.domain.entity.CreditEntry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { CurrencyIdMapper.class,
                 CustomerIdMapper.class,
                 CreditEntryIdMapper.class,
                 CustomerJpaToDomainMapper.class })
public interface CreditEntryJpaToDomainMapper extends JpaToDomainMapper<CreditEntryJpaEntity, CreditEntry> {

  @Mapping(target = "totalCredit.currencyId", source = "totalCreditCurrencyId")
  @Mapping(target = "totalCredit.amount", source = "totalCreditAmount")
  @Override
  CreditEntry convertJpaToDomain(CreditEntryJpaEntity creditEntryJpaEntity);
}
