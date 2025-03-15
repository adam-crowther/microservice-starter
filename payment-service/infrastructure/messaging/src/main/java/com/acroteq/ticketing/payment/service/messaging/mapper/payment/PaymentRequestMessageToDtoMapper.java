package com.acroteq.ticketing.payment.service.messaging.mapper.payment;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.CurrencyIdMapper;
import com.acroteq.application.mapper.id.CustomerIdMapper;
import com.acroteq.infrastructure.mapper.MessageToDtoMapper;
import com.acroteq.ticketing.kafka.payment.avro.model.PaymentRequestMessage;
import com.acroteq.ticketing.payment.service.domain.dto.payment.PaymentRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfig.class, uses = { CustomerIdMapper.class, CurrencyIdMapper.class })
public interface PaymentRequestMessageToDtoMapper extends MessageToDtoMapper<PaymentRequestMessage, PaymentRequestDto> {

  @Mapping(target = "value.amount", source = "message.valueAmount")
  @Mapping(target = "value.currencyId", source = "message.valueCurrencyId")
  @Override
  PaymentRequestDto convertMessageToDto(PaymentRequestMessage message, Integer partition, Long offset);
}
