package com.acroteq.ticketing.order.service.messaging.mapper.payment;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.ValidationResultMapper;
import com.acroteq.application.mapper.id.CurrencyIdMapper;
import com.acroteq.infrastructure.mapper.MessageToDtoMapper;
import com.acroteq.ticketing.kafka.payment.avro.model.PaymentPaidResponseMessage;
import com.acroteq.ticketing.order.service.domain.dto.message.PaymentPaidResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfig.class, uses = { CurrencyIdMapper.class, ValidationResultMapper.class })
public interface PaymentPaidResponseMessageToDtoMapper
    extends MessageToDtoMapper<PaymentPaidResponseMessage, PaymentPaidResponseDto> {

  @Mapping(target = "value.currencyId", source = "message.valueCurrencyId")
  @Mapping(target = "value.amount", source = "message.valueAmount")
  @Override
  PaymentPaidResponseDto convertMessageToDto(PaymentPaidResponseMessage message, Integer partition, Long offset);
}
