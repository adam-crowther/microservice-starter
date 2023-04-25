package com.acroteq.ticketing.payment.service.data.access.payment.mapper;

import com.acroteq.ticketing.application.mapper.CurrencyIdMapper;
import com.acroteq.ticketing.application.mapper.CustomerIdMapper;
import com.acroteq.ticketing.application.mapper.OrderIdMapper;
import com.acroteq.ticketing.application.mapper.PaymentIdMapper;
import com.acroteq.ticketing.payment.service.data.access.payment.entity.PaymentJpaEntity;
import com.acroteq.ticketing.payment.service.domain.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { CurrencyIdMapper.class, PaymentIdMapper.class, CustomerIdMapper.class, OrderIdMapper.class })
public interface PaymentDomainToJpaMapper {

  @Mapping(target = "valueCurrencyId", source = "value.currencyId")
  @Mapping(target = "valueAmount", source = "value.amount")
  @Mapping(target = "status", source = "paymentStatus")
  PaymentJpaEntity convertDomainToJpa(Payment payment);
}
