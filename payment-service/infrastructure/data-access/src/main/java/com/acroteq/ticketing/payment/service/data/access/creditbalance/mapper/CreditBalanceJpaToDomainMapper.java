package com.acroteq.ticketing.payment.service.data.access.creditbalance.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.CurrencyIdMapper;
import com.acroteq.application.mapper.id.CustomerIdMapper;
import com.acroteq.infrastructure.mapper.JpaToDomainMapper;
import com.acroteq.ticketing.payment.service.data.access.creditbalance.entity.CreditBalanceJpaEntity;
import com.acroteq.ticketing.payment.service.data.access.customer.mapper.CustomerJpaToDomainMapper;
import com.acroteq.ticketing.payment.service.domain.entity.CreditBalance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfig.class,
        uses = { CurrencyIdMapper.class,
                 CustomerIdMapper.class,
                 CreditBalanceIdMapper.class,
                 CustomerJpaToDomainMapper.class })
public interface CreditBalanceJpaToDomainMapper extends JpaToDomainMapper<CreditBalanceJpaEntity, CreditBalance> {

  @Mapping(target = "totalCredit.currencyId", source = "totalCreditCurrencyId")
  @Mapping(target = "totalCredit.amount", source = "totalCreditAmount")
  @Override
  CreditBalance convertJpaToDomain(CreditBalanceJpaEntity creditBalanceJpaEntity);
}
