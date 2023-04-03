package com.acroteq.food.ordering.system.order.service.domain.mapper;

import com.acroteq.food.ordering.system.common.application.mapper.IdMapper;
import com.acroteq.food.ordering.system.order.service.domain.dto.create.OrderItemDto;
import com.acroteq.food.ordering.system.order.service.domain.entity.OrderItem;
import com.acroteq.food.ordering.system.order.service.domain.resolver.ProductResolver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(uses = { IdMapper.class, ProductResolver.class})
public interface OrderItemDtoToDomainMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "orderId", ignore = true)
  @Mapping(target = "product", source = "productId")
  OrderItem convertDtoToDomain(OrderItemDto orderItems);

  List<OrderItem> convertDtoToDomain(List<OrderItemDto> orderItems);
}
