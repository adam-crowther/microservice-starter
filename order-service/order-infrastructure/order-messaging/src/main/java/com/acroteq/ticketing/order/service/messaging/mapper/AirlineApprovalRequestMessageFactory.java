package com.acroteq.ticketing.order.service.messaging.mapper;


import com.acroteq.ticketing.application.mapper.AirlineIdMapper;
import com.acroteq.ticketing.application.mapper.CurrencyIdMapper;
import com.acroteq.ticketing.application.mapper.DateTimeMapper;
import com.acroteq.ticketing.application.mapper.FlightIdMapper;
import com.acroteq.ticketing.application.mapper.OrderIdMapper;
import com.acroteq.ticketing.kafka.flight.approval.avro.model.AirlineApprovalRequestMessage;
import com.acroteq.ticketing.kafka.flight.approval.avro.model.Flight;
import com.acroteq.ticketing.order.service.domain.entity.OrderItem;
import com.acroteq.ticketing.order.service.domain.event.OrderEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.util.UUID;

@Mapper(uses = { OrderIdMapper.class,
                 CurrencyIdMapper.class,
                 AirlineIdMapper.class,
                 FlightIdMapper.class,
                 DateTimeMapper.class }, imports = { UUID.class, Instant.class })
public interface AirlineApprovalRequestMessageFactory {

  @Mapping(target = "orderId", source = "event.order.id")
  @Mapping(target = "airlineId", source = "event.order.airlineId")
  @Mapping(target = "flights", source = "event.order.items")
  @Mapping(target = "priceCurrencyId", source = "event.order.price.currencyId")
  @Mapping(target = "priceAmount", source = "event.order.price.amount")
  @Mapping(target = "airlineOrderStatus", constant = "PAID")
  AirlineApprovalRequestMessage createAirlineApprovalRequestMessage(OrderEvent event);

  @Mapping(target = "id", source = "flight.id")
  @Mapping(target = "quantity", source = "quantity")
  Flight convertFlight(OrderItem orderItem);
}
