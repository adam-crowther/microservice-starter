package com.acroteq.food.ordering.system.payment.service.messaging.mapper;

import com.acroteq.food.ordering.system.common.application.mapper.DateTimeMapper;
import com.acroteq.food.ordering.system.common.application.mapper.IdMapper;
import com.acroteq.food.ordering.system.common.application.mapper.ValidationResultMapper;
import com.acroteq.food.ordering.system.kafka.order.avro.model.PaymentResponseMessage;
import com.acroteq.food.ordering.system.payment.service.domain.event.PaymentCancelledEvent;
import com.acroteq.food.ordering.system.payment.service.domain.event.PaymentCompletedEvent;
import com.acroteq.food.ordering.system.payment.service.domain.event.PaymentFailedEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.util.UUID;

@Mapper(uses = { IdMapper.class, ValidationResultMapper.class, DateTimeMapper.class },
        imports = { UUID.class, Instant.class })
public interface PaymentResponseMessageFactory {

  @Mapping(target = "id", expression = "java(UUID.randomUUID().toString())")
  @Mapping(target = "sagaId", constant = "")
  @Mapping(target = "paymentId", source = "event.payment.id")
  @Mapping(target = "customerId", source = "event.payment.customerId")
  @Mapping(target = "orderId", source = "event.payment.id")
  @Mapping(target = "valueAmount", source = "event.payment.value.amount")
  @Mapping(target = "valueCurrencyId", source = "event.payment.value.currencyId")
  @Mapping(target = "paymentOrderStatus", source = "event.payment.paymentStatus")
  @Mapping(target = "failureMessages", source = "event.result")
  PaymentResponseMessage createPaymentResponseMessage(PaymentCompletedEvent event);

  @Mapping(target = "id", expression = "java(UUID.randomUUID().toString())")
  @Mapping(target = "sagaId", constant = "")
  @Mapping(target = "paymentId", source = "event.payment.id")
  @Mapping(target = "customerId", source = "event.payment.customerId")
  @Mapping(target = "orderId", source = "event.payment.id")
  @Mapping(target = "valueAmount", source = "event.payment.value.amount")
  @Mapping(target = "valueCurrencyId", source = "event.payment.value.currencyId")
  @Mapping(target = "paymentOrderStatus", source = "event.payment.paymentStatus")
  @Mapping(target = "failureMessages", source = "event.result")
  PaymentResponseMessage createPaymentResponseMessage(PaymentCancelledEvent event);

  @Mapping(target = "id", expression = "java(UUID.randomUUID().toString())")
  @Mapping(target = "sagaId", constant = "")
  @Mapping(target = "paymentId", source = "event.payment.id")
  @Mapping(target = "customerId", source = "event.payment.customerId")
  @Mapping(target = "orderId", source = "event.payment.id")
  @Mapping(target = "valueAmount", source = "event.payment.value.amount")
  @Mapping(target = "valueCurrencyId", source = "event.payment.value.currencyId")
  @Mapping(target = "paymentOrderStatus", source = "event.payment.paymentStatus")
  @Mapping(target = "failureMessages", source = "event.result")
  PaymentResponseMessage paymentFailedEventToPaymentResponseMessage(PaymentFailedEvent event);
}
