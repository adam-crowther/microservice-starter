package com.acroteq.ticketing.payment.service.data.access.credithistory.mapper;

import com.acroteq.ticketing.application.mapper.id.CurrencyIdMapper;
import com.acroteq.ticketing.application.mapper.id.CustomerIdMapper;
import com.acroteq.ticketing.infrastructure.mapper.JpaToDomainMapper;
import com.acroteq.ticketing.payment.service.data.access.credithistory.entity.CreditHistoryJpaEntity;
import com.acroteq.ticketing.payment.service.data.access.customer.mapper.CustomerJpaToDomainMapper;
import com.acroteq.ticketing.payment.service.domain.entity.CreditHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(uses = { CreditHistoryIdMapper.class,
                 CurrencyIdMapper.class,
                 CustomerIdMapper.class,
                 CustomerJpaToDomainMapper.class })
public interface CreditHistoryJpaToDomainMapper extends JpaToDomainMapper<CreditHistoryJpaEntity, CreditHistory> {

  @Mapping(target = "credit.currencyId", source = "creditCurrencyId")
  @Mapping(target = "credit.amount", source = "creditAmount")
  @Override
  CreditHistory convertJpaToDomain(CreditHistoryJpaEntity creditHistoryJpaEntity);

  List<CreditHistory> convertJpaToDomain(List<CreditHistoryJpaEntity> creditHistoryJpaEntity);
}
