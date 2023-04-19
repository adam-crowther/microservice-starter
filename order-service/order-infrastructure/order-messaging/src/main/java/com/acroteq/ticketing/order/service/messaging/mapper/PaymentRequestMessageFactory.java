package com.acroteq.ticketing.order.service.messaging.mapper;


import static com.acroteq.ticketing.kafka.order.avro.model.PaymentStatus.CANCELLED;
import static com.acroteq.ticketing.kafka.order.avro.model.PaymentStatus.PENDING;

import com.acroteq.ticketing.application.mapper.CurrencyIdMapper;
import com.acroteq.ticketing.application.mapper.CustomerIdMapper;
import com.acroteq.ticketing.application.mapper.DateTimeMapper;
import com.acroteq.ticketing.application.mapper.OrderIdMapper;
import com.acroteq.ticketing.kafka.order.avro.model.PaymentRequestMessage;
import com.acroteq.ticketing.kafka.order.avro.model.PaymentStatus;
import com.acroteq.ticketing.order.service.domain.event.OrderCancelledEvent;
import com.acroteq.ticketing.order.service.domain.event.OrderCreatedEvent;
import com.acroteq.ticketing.order.service.domain.event.OrderEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;


@Mapper(uses = { CustomerIdMapper.class, OrderIdMapper.class, CurrencyIdMapper.class, DateTimeMapper.class },
        imports = { UUID.class, Instant.class })
public abstract class PaymentRequestMessageFactory {

  private static final Map<Class<? extends OrderEvent>, PaymentStatus> STATUS_MAP = Map.of(OrderCreatedEvent.class,
                                                                                           PENDING,
                                                                                           OrderCancelledEvent.class,
                                                                                           CANCELLED);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "sagaId", constant = "")
  @Mapping(target = "customerId", source = "event.order.customerId")
  @Mapping(target = "orderId", source = "event.order.id")
  @Mapping(target = "valueAmount", source = "event.order.price.amount")
  @Mapping(target = "valueCurrencyId", source = "event.order.price.currencyId")
  @Mapping(target = "paymentStatus", expression = "java(getPaymentStatus(event))")
  public abstract PaymentRequestMessage createPaymentRequestMessage(OrderEvent event);

  PaymentStatus getPaymentStatus(final OrderEvent event) {
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
    return new UnsupportedOperationException("Can't generate a PaymentRequestMessage for OrderEvent type " + eventType);
  }
}
