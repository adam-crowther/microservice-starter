package com.acroteq.food.ordering.system.order.service.data.access.order.mapper;

import com.acroteq.food.ordering.system.common.application.mapper.IdMapper;
import com.acroteq.food.ordering.system.domain.valueobject.OrderId;
import com.acroteq.food.ordering.system.domain.valueobject.ProductId;
import com.acroteq.food.ordering.system.order.service.data.access.order.entity.OrderItemEntity;
import com.acroteq.food.ordering.system.order.service.domain.entity.OrderItem;
import com.acroteq.food.ordering.system.order.service.domain.resolver.ProductResolver;
import com.acroteq.food.ordering.system.order.service.domain.valueobject.OrderItemId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { IdMapper.class, ProductResolver.class },
        imports = { OrderItemId.class, OrderId.class, ProductId.class })
public interface OrderItemDomainToEntityMapper {

  @Mapping(target = "id", source = "orderItem.id")
  @Mapping(target = "order", ignore = true)
  @Mapping(target = "productId", source = "orderItem.product.id")
  OrderItemEntity convertDomainToEntity(OrderItem orderItem);
}
