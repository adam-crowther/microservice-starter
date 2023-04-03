package com.acroteq.food.ordering.system.order.service.messaging.mapper;


import com.acroteq.food.ordering.system.common.application.mapper.DateTimeMapper;
import com.acroteq.food.ordering.system.common.application.mapper.IdMapper;
import com.acroteq.food.ordering.system.kafka.order.avro.model.PaymentOrderStatus;
import com.acroteq.food.ordering.system.kafka.order.avro.model.PaymentRequestMessage;
import com.acroteq.food.ordering.system.order.service.domain.event.OrderCancelledEvent;
import com.acroteq.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.acroteq.food.ordering.system.order.service.domain.event.OrderEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;


@Mapper(uses = { IdMapper.class, DateTimeMapper.class },
        imports = { UUID.class, Instant.class })
public abstract class PaymentRequestMessageFactory {

  private static final Map<Class<? extends OrderEvent>, PaymentOrderStatus> STATUS_MAP = Map.of(OrderCreatedEvent.class,
                                                                                                PaymentOrderStatus.PENDING,
                                                                                                OrderCancelledEvent.class,
                                                                                                PaymentOrderStatus.CANCELLED);

  @Mapping(target = "id", expression = "java(UUID.randomUUID().toString())")
  @Mapping(target = "sagaId", constant = "")
  @Mapping(target = "customerId", source = "event.order.customerId")
  @Mapping(target = "orderId", source = "event.order.id")
  @Mapping(target = "valueAmount", source = "event.order.price.amount")
  @Mapping(target = "valueCurrencyId", source = "event.order.price.currencyId")
  @Mapping(target = "paymentOrderStatus", expression = "java(getOrderStatus(event))")
  public abstract PaymentRequestMessage createPaymentRequestMessage(OrderEvent event);

  PaymentOrderStatus getOrderStatus(final OrderEvent event) {
    return Optional.of(event)
                   .map(OrderEvent::getClass)
                   .map(STATUS_MAP::get)
                   .orElseThrow(unsupportedOperationException(event));
  }

  private Supplier<UnsupportedOperationException> unsupportedOperationException(final OrderEvent event) {
    return () -> createUnsupportedOperationException(event);
  }

  private UnsupportedOperationException createUnsupportedOperationException(final OrderEvent event) {
    final String eventType = event.getClass()
                                  .getSimpleName();
    return new UnsupportedOperationException("Can't generate a PaymentRequestMessage for OrderEvent type " +
                                             eventType);
  }
}
