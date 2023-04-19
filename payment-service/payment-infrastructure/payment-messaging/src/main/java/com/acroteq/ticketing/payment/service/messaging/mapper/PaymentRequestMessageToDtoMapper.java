package com.acroteq.ticketing.payment.service.messaging.mapper;

import com.acroteq.ticketing.application.mapper.CurrencyIdMapper;
import com.acroteq.ticketing.application.mapper.CustomerIdMapper;
import com.acroteq.ticketing.kafka.order.avro.model.PaymentRequestMessage;
import com.acroteq.ticketing.payment.service.domain.dto.PaymentRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { CustomerIdMapper.class, CurrencyIdMapper.class })
public interface PaymentRequestMessageToDtoMapper {

  @Mapping(target = "value.amount", source = "valueAmount")
  @Mapping(target = "value.currencyId", source = "valueCurrencyId")
  @Mapping(target = "paymentStatus", source = "paymentStatus")
  PaymentRequestDto paymentRequestMessageToPaymentRequest(PaymentRequestMessage paymentRequestMessage);
}
