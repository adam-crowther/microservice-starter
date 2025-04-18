package com.acroteq.ticketing.payment.service.messaging.mapper.payment;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.CurrencyIdMapper;
import com.acroteq.application.mapper.id.CustomerIdMapper;
import com.acroteq.infrastructure.mapper.MessageToDtoMapper;
import com.acroteq.ticketing.kafka.payment.avro.model.PaymentCancelRequestMessage;
import com.acroteq.ticketing.payment.service.domain.dto.payment.PaymentCancelRequestDto;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class, uses = { CustomerIdMapper.class, CurrencyIdMapper.class })
public interface PaymentCancelRequestMessageToDtoMapper
    extends MessageToDtoMapper<PaymentCancelRequestMessage, PaymentCancelRequestDto> {

  @Override
  PaymentCancelRequestDto convertMessageToDto(PaymentCancelRequestMessage message, Integer partition, Long offset);
}
