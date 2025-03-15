package com.acroteq.ticketing.order.service.messaging.mapper.payment;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.ValidationResultMapper;
import com.acroteq.application.mapper.id.CurrencyIdMapper;
import com.acroteq.infrastructure.mapper.MessageToDtoMapper;
import com.acroteq.ticketing.kafka.payment.avro.model.PaymentFailedResponseMessage;
import com.acroteq.ticketing.order.service.domain.dto.message.PaymentCancelledResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfig.class, uses = { CurrencyIdMapper.class, ValidationResultMapper.class })
public interface PaymentFailedResponseMessageToDtoMapper
    extends MessageToDtoMapper<PaymentFailedResponseMessage, PaymentCancelledResponseDto> {

  @Mapping(target = "value.currencyId", source = "message.valueCurrencyId")
  @Mapping(target = "value.amount", source = "message.valueAmount")
  @Mapping(target = "result", source = "message.failureMessages")
  @Override
  PaymentCancelledResponseDto convertMessageToDto(PaymentFailedResponseMessage message, Integer partition, Long offset);
}
