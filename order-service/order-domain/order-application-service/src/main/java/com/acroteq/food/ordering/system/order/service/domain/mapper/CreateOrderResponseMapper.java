package com.acroteq.food.ordering.system.order.service.domain.mapper;

import com.acroteq.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.acroteq.food.ordering.system.order.service.domain.entity.Order;
import com.acroteq.food.ordering.system.order.service.domain.entity.OrderItem;
import com.acroteq.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.acroteq.food.ordering.system.order.service.domain.resolver.RestaurantResolver;
import com.acroteq.food.ordering.system.order.service.domain.valueobject.TrackingId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

@Mapper
public interface CreateOrderResponseMapper {

  @Mapping(target = "trackingId", source = "trackingId.id")
  @Mapping(target = "status", source = "orderStatus")
  @Mapping(target = "message", constant = "TODO")
  CreateOrderResponse convert(Order order);
}
