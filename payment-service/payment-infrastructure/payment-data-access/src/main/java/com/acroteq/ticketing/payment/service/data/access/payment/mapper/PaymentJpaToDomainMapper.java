package com.acroteq.ticketing.payment.service.data.access.payment.mapper;

import com.acroteq.ticketing.application.mapper.CurrencyIdMapper;
import com.acroteq.ticketing.application.mapper.CustomerIdMapper;
import com.acroteq.ticketing.application.mapper.OrderIdMapper;
import com.acroteq.ticketing.application.mapper.PaymentIdMapper;
import com.acroteq.ticketing.payment.service.data.access.payment.entity.PaymentJpaEntity;
import com.acroteq.ticketing.payment.service.domain.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { PaymentIdMapper.class, CurrencyIdMapper.class, OrderIdMapper.class, CustomerIdMapper.class })
public interface PaymentJpaToDomainMapper {

  @Mapping(target = "value.currencyId", source = "valueCurrencyId")
  @Mapping(target = "value.amount", source = "valueAmount")
  @Mapping(target = "paymentStatus", source = "status")
  Payment convertEntityToDomain(PaymentJpaEntity paymentJpaEntity);
}
