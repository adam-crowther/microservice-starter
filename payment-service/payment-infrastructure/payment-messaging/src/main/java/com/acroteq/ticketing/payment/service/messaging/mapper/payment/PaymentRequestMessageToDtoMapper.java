package com.acroteq.ticketing.payment.service.messaging.mapper.payment;

import com.acroteq.ticketing.application.mapper.id.CurrencyIdMapper;
import com.acroteq.ticketing.application.mapper.id.CustomerIdMapper;
import com.acroteq.ticketing.infrastructure.mapper.MessageToDtoMapper;
import com.acroteq.ticketing.kafka.payment.avro.model.PaymentRequestMessage;
import com.acroteq.ticketing.payment.service.domain.dto.payment.PaymentRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { CustomerIdMapper.class, CurrencyIdMapper.class })
public interface PaymentRequestMessageToDtoMapper extends MessageToDtoMapper<PaymentRequestMessage, PaymentRequestDto> {

  @Mapping(target = "value.amount", source = "valueAmount")
  @Mapping(target = "value.currencyId", source = "valueCurrencyId")
  @Override
  PaymentRequestDto convertMessageToDto(PaymentRequestMessage paymentRequestMessage);
}
