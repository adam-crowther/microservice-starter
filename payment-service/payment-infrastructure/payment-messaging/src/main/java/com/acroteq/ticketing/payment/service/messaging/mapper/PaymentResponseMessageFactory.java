package com.acroteq.ticketing.payment.service.messaging.mapper;

import com.acroteq.ticketing.application.mapper.CurrencyIdMapper;
import com.acroteq.ticketing.application.mapper.CustomerIdMapper;
import com.acroteq.ticketing.application.mapper.DateTimeMapper;
import com.acroteq.ticketing.application.mapper.OrderIdMapper;
import com.acroteq.ticketing.application.mapper.PaymentIdMapper;
import com.acroteq.ticketing.application.mapper.ValidationResultMapper;
import com.acroteq.ticketing.kafka.payment.avro.model.PaymentResponseMessage;
import com.acroteq.ticketing.payment.service.domain.event.PaymentCancelledEvent;
import com.acroteq.ticketing.payment.service.domain.event.PaymentCompletedEvent;
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
public interface PaymentResponseMessageFactory {

  @Mapping(target = "paymentId", source = "event.payment.id")
  @Mapping(target = "customerId", source = "event.payment.customerId")
  @Mapping(target = "orderId", source = "event.payment.orderId")
  @Mapping(target = "valueAmount", source = "event.payment.value.amount")
  @Mapping(target = "valueCurrencyId", source = "event.payment.value.currencyId")
  @Mapping(target = "paymentStatus", source = "event.payment.paymentStatus")
  @Mapping(target = "failureMessages", source = "event.result")
  PaymentResponseMessage createPaymentResponseMessage(PaymentCompletedEvent event);

  @Mapping(target = "paymentId", source = "event.payment.id")
  @Mapping(target = "customerId", source = "event.payment.customerId")
  @Mapping(target = "orderId", source = "event.payment.orderId")
  @Mapping(target = "valueAmount", source = "event.payment.value.amount")
  @Mapping(target = "valueCurrencyId", source = "event.payment.value.currencyId")
  @Mapping(target = "paymentStatus", source = "event.payment.paymentStatus")
  @Mapping(target = "failureMessages", source = "event.result")
  PaymentResponseMessage createPaymentResponseMessage(PaymentCancelledEvent event);

  @Mapping(target = "paymentId", source = "event.payment.id")
  @Mapping(target = "customerId", source = "event.payment.customerId")
  @Mapping(target = "orderId", source = "event.payment.orderId")
  @Mapping(target = "valueAmount", source = "event.payment.value.amount")
  @Mapping(target = "valueCurrencyId", source = "event.payment.value.currencyId")
  @Mapping(target = "paymentStatus", source = "event.payment.paymentStatus")
  @Mapping(target = "failureMessages", source = "event.result")
  PaymentResponseMessage paymentFailedEventToPaymentResponseMessage(PaymentFailedEvent event);
}
