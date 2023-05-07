package com.acroteq.ticketing.payment.service.data.access.payment.mapper;

import com.acroteq.ticketing.application.mapper.id.CurrencyIdMapper;
import com.acroteq.ticketing.application.mapper.id.CustomerIdMapper;
import com.acroteq.ticketing.application.mapper.id.OrderIdMapper;
import com.acroteq.ticketing.application.mapper.id.PaymentIdMapper;
import com.acroteq.ticketing.infrastructure.mapper.DomainToJpaMapper;
import com.acroteq.ticketing.payment.service.data.access.customer.resolver.CustomerJpaResolver;
import com.acroteq.ticketing.payment.service.data.access.payment.entity.PaymentJpaEntity;
import com.acroteq.ticketing.payment.service.domain.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(uses = { CurrencyIdMapper.class,
                 PaymentIdMapper.class,
                 CustomerIdMapper.class,
                 OrderIdMapper.class,
                 CustomerJpaResolver.class })
public interface PaymentDomainToJpaMapper extends DomainToJpaMapper<Payment, PaymentJpaEntity> {

  @Mapping(target = "audit", ignore = true)
  @Mapping(target = "customer", source = "customer.id")
  @Mapping(target = "valueCurrencyId", source = "value.currencyId")
  @Mapping(target = "valueAmount", source = "value.amount")
  @Mapping(target = "status", source = "paymentStatus")
  @Override
  PaymentJpaEntity convertDomainToJpa(Payment entity);

  @Mapping(target = "audit", ignore = true)
  @Mapping(target = "customer", source = "customer.id")
  @Mapping(target = "valueCurrencyId", source = "value.currencyId")
  @Mapping(target = "valueAmount", source = "value.amount")
  @Mapping(target = "status", source = "paymentStatus")
  @Override
  PaymentJpaEntity convertDomainToJpa(Payment entity, @MappingTarget PaymentJpaEntity jpaEntity);
}
