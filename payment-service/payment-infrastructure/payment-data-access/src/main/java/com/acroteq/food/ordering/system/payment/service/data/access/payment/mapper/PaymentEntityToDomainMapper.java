package com.acroteq.food.ordering.system.payment.service.data.access.payment.mapper;

import com.acroteq.food.ordering.system.common.application.mapper.IdMapper;
import com.acroteq.food.ordering.system.payment.service.data.access.payment.entity.PaymentEntity;
import com.acroteq.food.ordering.system.payment.service.domain.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { PaymentIdMapper.class, IdMapper.class })
public interface PaymentEntityToDomainMapper {

  @Mapping(target = "value.currencyId", source = "valueCurrencyId")
  @Mapping(target = "value.amount", source = "valueAmount")
  @Mapping(target = "paymentStatus", source = "status")
  Payment convertEntityToDomain(PaymentEntity paymentEntity);
}
