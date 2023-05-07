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

  @Mapping(target = "paymentId", source = "payment.id")
  @Mapping(target = "paymentVersion", source = "payment.version")
  @Mapping(target = "audit", source = "payment.audit")
  @Mapping(target = "customerId", source = "payment.customer.id")
  @Mapping(target = "orderId", source = "payment.orderId")
  @Mapping(target = "orderVersion", source = "payment.orderVersion")
  @Mapping(target = "valueAmount", source = "payment.value.amount")
  @Mapping(target = "valueCurrencyId", source = "payment.value.currencyId")
  @Mapping(target = "failureMessages", source = "result")
  @Mapping(target = "auditBuilder", ignore = true)
  @Override
  PaymentFailedResponseMessage convertEventToMessage(PaymentFailedEvent event);
}
