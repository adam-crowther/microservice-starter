package com.acroteq.food.ordering.system.payment.service.domain.mapper;

import com.acroteq.food.ordering.system.common.application.mapper.DateTimeMapper;
import com.acroteq.food.ordering.system.common.application.mapper.IdMapper;
import com.acroteq.food.ordering.system.domain.valueobject.PaymentOrderStatus;
import com.acroteq.food.ordering.system.domain.valueobject.PaymentStatus;
import com.acroteq.food.ordering.system.payment.service.domain.dto.PaymentRequestDto;
import com.acroteq.food.ordering.system.payment.service.domain.entity.Payment;
import com.acroteq.food.ordering.system.payment.service.domain.valueobject.PaymentId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(uses = { IdMapper.class, DateTimeMapper.class })
public interface PaymentDtoToDomainMapper {

  @Mapping(target = "paymentStatus", source = "paymentOrderStatus")
  Payment convertDtoToDomain(final PaymentRequestDto paymentRequestDto);

  @Mapping(target = "value", source = "uuid")
  PaymentId convertUuidToPaymentId(UUID uuid);

  PaymentStatus convertPaymentStatus(final PaymentOrderStatus paymentOrderStatus);
}
