package com.acroteq.ticketing.order.service.messaging.mapper.airline;


import com.acroteq.ticketing.application.mapper.DateTimeMapper;
import com.acroteq.ticketing.application.mapper.MapstructConfig;
import com.acroteq.ticketing.application.mapper.id.AirlineIdMapper;
import com.acroteq.ticketing.application.mapper.id.CurrencyIdMapper;
import com.acroteq.ticketing.application.mapper.id.FlightIdMapper;
import com.acroteq.ticketing.application.mapper.id.OrderIdMapper;
import com.acroteq.ticketing.infrastructure.mapper.EventToMessageMapper;
import com.acroteq.ticketing.kafka.flight.approval.avro.model.AirlineApprovalRequestMessage;
import com.acroteq.ticketing.kafka.flight.approval.avro.model.Flight;
import com.acroteq.ticketing.order.service.domain.entity.OrderItem;
import com.acroteq.ticketing.order.service.domain.event.OrderEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.util.UUID;

@Mapper(config = MapstructConfig.class,
        uses = { OrderIdMapper.class,
                 CurrencyIdMapper.class,
                 AirlineIdMapper.class,
                 FlightIdMapper.class,
                 DateTimeMapper.class },
        imports = { UUID.class, Instant.class })
public interface AirlineApprovalRequestMessageFactory
    extends EventToMessageMapper<OrderEvent, AirlineApprovalRequestMessage> {

  @Mapping(target = "orderId", source = "order.id")
  @Mapping(target = "orderVersion", source = "order.version")
  @Mapping(target = "airlineId", source = "order.airline.id")
  @Mapping(target = "flights", source = "order.items")
  @Mapping(target = "priceCurrencyId", source = "order.price.currencyId")
  @Mapping(target = "priceAmount", source = "order.price.amount")
  @Mapping(target = "airlineOrderStatus", constant = "PAID")
  @Override
  AirlineApprovalRequestMessage convertEventToMessage(OrderEvent event);

  @Mapping(target = "id", source = "flight.id")
  @Mapping(target = "quantity", source = "quantity")
  Flight convertEventToMessage(OrderItem orderItem);
}
