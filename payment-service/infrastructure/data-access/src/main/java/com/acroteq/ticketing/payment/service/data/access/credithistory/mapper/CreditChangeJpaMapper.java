package com.acroteq.ticketing.payment.service.data.access.credithistory.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.CurrencyIdMapper;
import com.acroteq.application.mapper.id.CustomerIdMapper;
import com.acroteq.infrastructure.mapper.JpaMapper;
import com.acroteq.mapstruct.Resolve;
import com.acroteq.ticketing.payment.service.data.access.credithistory.entity.CreditChangeJpaEntity;
import com.acroteq.ticketing.payment.service.data.access.credithistory.repository.CreditChangeJpaRepository;
import com.acroteq.ticketing.payment.service.data.access.customer.mapper.CustomerJpaMapper;
import com.acroteq.ticketing.payment.service.domain.entity.CreditChange;
import lombok.Getter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(config = MapstructConfig.class,
        uses = { CreditChangeIdMapper.class, CurrencyIdMapper.class, CustomerIdMapper.class, CustomerJpaMapper.class })
public abstract class CreditChangeJpaMapper implements JpaMapper<CreditChange, CreditChangeJpaEntity> {

  @Getter
  @Autowired
  private CreditChangeJpaRepository repository;

  @Mapping(target = "creditDelta.currencyId", source = "creditDeltaCurrencyId")
  @Mapping(target = "creditDelta.amount", source = "creditDeltaAmount")
  @Override
  public abstract CreditChange convert(CreditChangeJpaEntity creditChangeJpaEntity);

  public List<CreditChange> convert(final List<CreditChangeJpaEntity> entities) {
    return entities.stream()
                   .map(this::convert)
                   .toList();
  }

  @Mapping(target = "audit", ignore = true)
  @Mapping(target = "creditDeltaCurrencyId", source = "creditDelta.currencyId")
  @Mapping(target = "creditDeltaAmount", source = "creditDelta.amount")
  @Mapping(target = "customer", qualifiedBy = Resolve.class)
  @Override
  public abstract CreditChangeJpaEntity convertNew(CreditChange entity);

  @Mapping(target = "audit", ignore = true)
  @Mapping(target = "creditDeltaCurrencyId", source = "creditDelta.currencyId")
  @Mapping(target = "creditDeltaAmount", source = "creditDelta.amount")
  @Mapping(target = "customer", qualifiedBy = Resolve.class)
  @Override
  public abstract CreditChangeJpaEntity convertExisting(
      CreditChange entity,
      @MappingTarget CreditChangeJpaEntity jpaEntity);
}
