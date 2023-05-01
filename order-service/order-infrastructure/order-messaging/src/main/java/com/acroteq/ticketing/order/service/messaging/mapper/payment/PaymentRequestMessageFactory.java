package com.acroteq.ticketing.order.service.messaging.mapper.payment;


import com.acroteq.ticketing.application.mapper.DateTimeMapper;
import com.acroteq.ticketing.application.mapper.id.CurrencyIdMapper;
import com.acroteq.ticketing.application.mapper.id.CustomerIdMapper;
import com.acroteq.ticketing.application.mapper.id.OrderIdMapper;
import com.acroteq.ticketing.infrastructure.mapper.EventToMessageMapper;
import com.acroteq.ticketing.kafka.payment.avro.model.PaymentRequestMessage;
import com.acroteq.ticketing.order.service.domain.event.OrderEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.util.UUID;


@Mapper(uses = { CustomerIdMapper.class, OrderIdMapper.class, CurrencyIdMapper.class, DateTimeMapper.class },
        imports = { UUID.class, Instant.class })
public interface PaymentRequestMessageFactory extends EventToMessageMapper<OrderEvent, PaymentRequestMessage> {

  @Mapping(target = "customerId", source = "event.order.customerId")
  @Mapping(target = "orderId", source = "event.order.id")
  @Mapping(target = "valueAmount", source = "event.order.price.amount")
  @Mapping(target = "valueCurrencyId", source = "event.order.price.currencyId")
  @Override
  PaymentRequestMessage convertEventToMessage(OrderEvent event);
}
