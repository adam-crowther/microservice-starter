package com.acroteq.ticketing.order.service.presentation.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.ticketing.order.service.domain.entity.OrderItem;
import com.acroteq.ticketing.order.service.domain.resolver.FlightResolver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfig.class, uses = { FlightResolver.class })
public interface OrderItemMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "version", ignore = true)
  @Mapping(target = "flight", source = "flightId")
  OrderItem convert(com.acroteq.ticketing.order.service.presentation.model.CreateOrderItem orderItem);

  @Mapping(target = "flightId", source = "flight.id.value")
  com.acroteq.ticketing.order.service.presentation.model.OrderItem convert(OrderItem orderItem);
}
