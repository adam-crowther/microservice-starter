package com.acroteq.food.ordering.system.order.service.messaging.mapper;


import com.acroteq.food.ordering.system.common.application.mapper.DateTimeMapper;
import com.acroteq.food.ordering.system.common.application.mapper.IdMapper;
import com.acroteq.food.ordering.system.kafka.order.avro.model.Product;
import com.acroteq.food.ordering.system.kafka.order.avro.model.RestaurantApprovalRequestMessage;
import com.acroteq.food.ordering.system.order.service.domain.entity.OrderItem;
import com.acroteq.food.ordering.system.order.service.domain.event.OrderEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.util.UUID;

@Mapper(uses = { IdMapper.class,
                 DateTimeMapper.class },
        imports = { UUID.class, Instant.class })
public interface RestaurantApprovalRequestMessageFactory {

  @Mapping(target = "id", expression = "java(UUID.randomUUID().toString())")
  @Mapping(target = "sagaId", constant = "")
  @Mapping(target = "orderId", source = "event.order.id")
  @Mapping(target = "restaurantId", source = "event.order.id")
  @Mapping(target = "products", source = "event.order.items")
  @Mapping(target = "priceCurrencyId", source = "event.order.price.currencyId")
  @Mapping(target = "priceAmount", source = "event.order.price.amount")
  @Mapping(target = "restaurantOrderStatus", constant = "PAID")
  RestaurantApprovalRequestMessage createRestaurantApprovalRequestMessage(OrderEvent event);

  @Mapping(target = "id", source = "product.id")
  @Mapping(target = "quantity", source = "quantity")
  Product convertProduct(final OrderItem orderItem);
}
