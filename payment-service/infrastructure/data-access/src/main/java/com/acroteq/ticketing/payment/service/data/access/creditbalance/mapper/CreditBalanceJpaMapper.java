package com.acroteq.ticketing.payment.service.data.access.creditbalance.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.CurrencyIdMapper;
import com.acroteq.application.mapper.id.CustomerIdMapper;
import com.acroteq.infrastructure.mapper.JpaMapper;
import com.acroteq.mapstruct.Resolve;
import com.acroteq.ticketing.payment.service.data.access.creditbalance.entity.CreditBalanceJpaEntity;
import com.acroteq.ticketing.payment.service.data.access.creditbalance.repository.CreditBalanceJpaRepository;
import com.acroteq.ticketing.payment.service.data.access.customer.mapper.CustomerJpaMapper;
import com.acroteq.ticketing.payment.service.domain.entity.CreditBalance;
import lombok.Getter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(config = MapstructConfig.class,
        uses = { CurrencyIdMapper.class, CustomerIdMapper.class, CreditBalanceIdMapper.class, CustomerJpaMapper.class })
public abstract class CreditBalanceJpaMapper implements JpaMapper<CreditBalance, CreditBalanceJpaEntity> {

  @Getter
  @Autowired
  private CreditBalanceJpaRepository repository;

  @Mapping(target = "totalCredit.currencyId", source = "totalCreditCurrencyId")
  @Mapping(target = "totalCredit.amount", source = "totalCreditAmount")
  @Override
  public abstract CreditBalance convert(CreditBalanceJpaEntity creditBalanceJpaEntity);

  @Mapping(target = "audit", ignore = true)
  @Mapping(target = "totalCreditCurrencyId", source = "totalCredit.currencyId")
  @Mapping(target = "totalCreditAmount", source = "totalCredit.amount")
  @Mapping(target = "customer", qualifiedBy = Resolve.class)
  @Override
  public abstract CreditBalanceJpaEntity convertNew(CreditBalance entity);

  @Mapping(target = "audit", ignore = true)
  @Mapping(target = "totalCreditCurrencyId", source = "totalCredit.currencyId")
  @Mapping(target = "totalCreditAmount", source = "totalCredit.amount")
  @Mapping(target = "customer", qualifiedBy = Resolve.class)
  @Override
  public abstract CreditBalanceJpaEntity convertExisting(
      CreditBalance entity,
      @MappingTarget CreditBalanceJpaEntity jpaEntity);
}
