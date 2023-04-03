package com.acroteq.food.ordering.system.payment.service.messaging.mapper;

import com.acroteq.food.ordering.system.common.application.mapper.IdMapper;
import com.acroteq.food.ordering.system.kafka.order.avro.model.PaymentRequestMessage;
import com.acroteq.food.ordering.system.payment.service.domain.dto.PaymentRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { PaymentOrderStatusMapper.class, IdMapper.class })
public interface PaymentRequestMessageToDtoMapper {

  @Mapping(target = "value.amount", source = "valueAmount")
  @Mapping(target = "value.currencyId", source = "valueCurrencyId")
  PaymentRequestDto paymentRequestMessageToPaymentRequest(PaymentRequestMessage paymentRequestMessage);
}
