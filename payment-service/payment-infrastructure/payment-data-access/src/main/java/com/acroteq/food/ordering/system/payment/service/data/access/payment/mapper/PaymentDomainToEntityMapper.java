package com.acroteq.food.ordering.system.payment.service.data.access.payment.mapper;

import com.acroteq.food.ordering.system.common.application.mapper.IdMapper;
import com.acroteq.food.ordering.system.payment.service.data.access.payment.entity.PaymentEntity;
import com.acroteq.food.ordering.system.payment.service.domain.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = IdMapper.class)
public interface PaymentDomainToEntityMapper {

  @Mapping(target = "valueCurrencyId", source = "value.currencyId")
  @Mapping(target = "valueAmount", source = "value.amount")
  @Mapping(target = "status", source = "paymentStatus")
  PaymentEntity convertDomainToEntity(Payment payment);
}
