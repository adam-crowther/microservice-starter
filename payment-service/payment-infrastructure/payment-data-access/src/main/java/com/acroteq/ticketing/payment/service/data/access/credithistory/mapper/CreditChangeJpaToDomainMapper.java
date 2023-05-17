package com.acroteq.ticketing.payment.service.data.access.credithistory.mapper;

import com.acroteq.ticketing.application.mapper.id.CurrencyIdMapper;
import com.acroteq.ticketing.application.mapper.id.CustomerIdMapper;
import com.acroteq.ticketing.infrastructure.mapper.JpaToDomainMapper;
import com.acroteq.ticketing.payment.service.data.access.credithistory.entity.CreditChangeJpaEntity;
import com.acroteq.ticketing.payment.service.data.access.customer.mapper.CustomerJpaToDomainMapper;
import com.acroteq.ticketing.payment.service.domain.entity.CreditChange;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(uses = { CreditChangeIdMapper.class,
                 CurrencyIdMapper.class,
                 CustomerIdMapper.class,
                 CustomerJpaToDomainMapper.class })
public interface CreditChangeJpaToDomainMapper extends JpaToDomainMapper<CreditChangeJpaEntity, CreditChange> {

  @Mapping(target = "creditDelta.currencyId", source = "creditDeltaCurrencyId")
  @Mapping(target = "creditDelta.amount", source = "creditDeltaAmount")
  @Override
  CreditChange convertJpaToDomain(CreditChangeJpaEntity creditChangeJpaEntity);

  List<CreditChange> convertJpaToDomain(List<CreditChangeJpaEntity> creditChangeJpaEntity);
}
