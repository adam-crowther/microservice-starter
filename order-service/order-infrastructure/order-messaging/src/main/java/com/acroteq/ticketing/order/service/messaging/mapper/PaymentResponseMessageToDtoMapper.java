package com.acroteq.ticketing.order.service.messaging.mapper;

import com.acroteq.ticketing.application.mapper.CurrencyIdMapper;
import com.acroteq.ticketing.application.mapper.ValidationResultMapper;
import com.acroteq.ticketing.kafka.payment.avro.model.PaymentResponseMessage;
import com.acroteq.ticketing.order.service.domain.dto.message.PaymentResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { CurrencyIdMapper.class, ValidationResultMapper.class })
public interface PaymentResponseMessageToDtoMapper {

  @Mapping(target = "value.currencyId", source = "valueCurrencyId")
  @Mapping(target = "value.amount", source = "valueAmount")
  @Mapping(target = "result", source = "failureMessages")
  PaymentResponseDto convertMessageToDto(PaymentResponseMessage message);
}
