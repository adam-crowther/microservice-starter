package com.acroteq.food.ordering.system.order.service.domain.mapper;

import com.acroteq.food.ordering.system.order.service.domain.entity.OrderItem;
import com.acroteq.food.ordering.system.order.service.domain.resolver.ProductResolver;
import com.acroteq.food.ordering.system.order.service.domain.resolver.RestaurantResolver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(uses = {ProductResolver.class})
public interface OrderItemMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "orderId", ignore = true)
  @Mapping(target = "product", source = "productId")
  OrderItem convert(com.acroteq.food.ordering.system.order.service.domain.dto.create.OrderItem orderItems);

  List<OrderItem> convert(List<com.acroteq.food.ordering.system.order.service.domain.dto.create.OrderItem> orderItems);
}
