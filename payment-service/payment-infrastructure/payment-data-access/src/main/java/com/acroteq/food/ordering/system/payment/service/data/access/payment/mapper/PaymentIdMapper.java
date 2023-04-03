package com.acroteq.food.ordering.system.payment.service.data.access.payment.mapper;

import com.acroteq.food.ordering.system.payment.service.domain.valueobject.PaymentId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper
public interface PaymentIdMapper {

  @Mapping(target = "value", source = "id")
  PaymentId convertUuidToPaymentId(final UUID id);

  default UUID convertPaymentIdToUuid(final PaymentId paymentId) {
    return paymentId.getValue();
  }
}
