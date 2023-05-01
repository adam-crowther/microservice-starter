package com.acroteq.ticketing.payment.service.messaging.mapper.payment;

import com.acroteq.ticketing.application.mapper.DateTimeMapper;
import com.acroteq.ticketing.application.mapper.ValidationResultMapper;
import com.acroteq.ticketing.application.mapper.id.CurrencyIdMapper;
import com.acroteq.ticketing.application.mapper.id.CustomerIdMapper;
import com.acroteq.ticketing.application.mapper.id.OrderIdMapper;
import com.acroteq.ticketing.application.mapper.id.PaymentIdMapper;
import com.acroteq.ticketing.infrastructure.mapper.EventToMessageMapper;
import com.acroteq.ticketing.kafka.payment.avro.model.PaymentFailedResponseMessage;
import com.acroteq.ticketing.payment.service.domain.event.PaymentFailedEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.util.UUID;

@Mapper(uses = { PaymentIdMapper.class,
                 OrderIdMapper.class,
                 CustomerIdMapper.class,
                 CurrencyIdMapper.class,
                 ValidationResultMapper.class,
                 DateTimeMapper.class }, imports = { UUID.class, Instant.class })
public interface PaymentFailedResponseMessageFactory
    extends EventToMessageMapper<PaymentFailedEvent, PaymentFailedResponseMessage> {

  @Mapping(target = "paymentId", source = "event.payment.id")
  @Mapping(target = "customerId", source = "event.payment.customerId")
  @Mapping(target = "orderId", source = "event.payment.orderId")
  @Mapping(target = "valueAmount", source = "event.payment.value.amount")
  @Mapping(target = "valueCurrencyId", source = "event.payment.value.currencyId")
  @Mapping(target = "failureMessages", source = "event.result")
  @Override
  PaymentFailedResponseMessage convertEventToMessage(PaymentFailedEvent event);
}
