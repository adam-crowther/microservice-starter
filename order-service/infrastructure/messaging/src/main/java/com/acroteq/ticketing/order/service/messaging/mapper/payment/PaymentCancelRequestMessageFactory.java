package com.acroteq.ticketing.order.service.messaging.mapper.payment;


import com.acroteq.ticketing.application.mapper.DateTimeMapper;
import com.acroteq.ticketing.application.mapper.MapstructConfig;
import com.acroteq.ticketing.application.mapper.id.CurrencyIdMapper;
import com.acroteq.ticketing.application.mapper.id.CustomerIdMapper;
import com.acroteq.ticketing.application.mapper.id.OrderIdMapper;
import com.acroteq.ticketing.infrastructure.mapper.EventToMessageMapper;
import com.acroteq.ticketing.kafka.payment.avro.model.PaymentCancelRequestMessage;
import com.acroteq.ticketing.order.service.domain.event.OrderEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.util.UUID;


@Mapper(config = MapstructConfig.class,
        uses = { CustomerIdMapper.class, OrderIdMapper.class, CurrencyIdMapper.class, DateTimeMapper.class },
        imports = { UUID.class, Instant.class })
public interface PaymentCancelRequestMessageFactory
    extends EventToMessageMapper<OrderEvent, PaymentCancelRequestMessage> {

  @Mapping(target = "orderId", source = "order.id")
  @Mapping(target = "orderVersion", source = "order.version")
  @Override
  PaymentCancelRequestMessage convertEventToMessage(OrderEvent event);
}
