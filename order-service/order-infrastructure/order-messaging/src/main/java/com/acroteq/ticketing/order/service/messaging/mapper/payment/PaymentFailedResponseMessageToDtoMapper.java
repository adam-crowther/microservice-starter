package com.acroteq.ticketing.order.service.messaging.mapper.payment;

import com.acroteq.ticketing.application.mapper.ValidationResultMapper;
import com.acroteq.ticketing.application.mapper.id.CurrencyIdMapper;
import com.acroteq.ticketing.infrastructure.mapper.MessageToDtoMapper;
import com.acroteq.ticketing.kafka.payment.avro.model.PaymentFailedResponseMessage;
import com.acroteq.ticketing.order.service.domain.dto.message.PaymentCancelledResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { CurrencyIdMapper.class, ValidationResultMapper.class })
public interface PaymentFailedResponseMessageToDtoMapper
    extends MessageToDtoMapper<PaymentFailedResponseMessage, PaymentCancelledResponseDto> {

  @Mapping(target = "value.currencyId", source = "valueCurrencyId")
  @Mapping(target = "value.amount", source = "valueAmount")
  @Mapping(target = "result", source = "failureMessages")
  @Override
  PaymentCancelledResponseDto convertMessageToDto(PaymentFailedResponseMessage message);
}
