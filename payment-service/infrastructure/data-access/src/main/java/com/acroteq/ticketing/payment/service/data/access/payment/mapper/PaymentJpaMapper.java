package com.acroteq.ticketing.payment.service.data.access.payment.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.CurrencyIdMapper;
import com.acroteq.application.mapper.id.CustomerIdMapper;
import com.acroteq.application.mapper.id.OrderIdMapper;
import com.acroteq.application.mapper.id.PaymentIdMapper;
import com.acroteq.infrastructure.mapper.JpaMapper;
import com.acroteq.mapstruct.Resolve;
import com.acroteq.ticketing.payment.service.data.access.customer.mapper.CustomerJpaMapper;
import com.acroteq.ticketing.payment.service.data.access.payment.entity.PaymentJpaEntity;
import com.acroteq.ticketing.payment.service.data.access.payment.repository.PaymentJpaRepository;
import com.acroteq.ticketing.payment.service.domain.entity.Payment;
import lombok.Getter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(config = MapstructConfig.class,
        uses = { CurrencyIdMapper.class,
                 PaymentIdMapper.class,
                 CustomerIdMapper.class,
                 OrderIdMapper.class,
                 CustomerJpaMapper.class })
public abstract class PaymentJpaMapper implements JpaMapper<Payment, PaymentJpaEntity> {

  @Getter
  @Autowired
  private PaymentJpaRepository repository;

  @Mapping(target = "value.currencyId", source = "valueCurrencyId")
  @Mapping(target = "value.amount", source = "valueAmount")
  @Override
  public abstract Payment convert(PaymentJpaEntity paymentJpaEntity);

  @Mapping(target = "audit", ignore = true)
  @Mapping(target = "customer", qualifiedBy = Resolve.class)
  @Mapping(target = "valueCurrencyId", source = "value.currencyId")
  @Mapping(target = "valueAmount", source = "value.amount")
  @Override
  public abstract PaymentJpaEntity convertNew(Payment entity);

  @Mapping(target = "audit", ignore = true)
  @Mapping(target = "customer", qualifiedBy = Resolve.class)
  @Mapping(target = "valueCurrencyId", source = "value.currencyId")
  @Mapping(target = "valueAmount", source = "value.amount")
  @Override
  public abstract PaymentJpaEntity convertExisting(Payment entity, @MappingTarget PaymentJpaEntity jpaEntity);
}
