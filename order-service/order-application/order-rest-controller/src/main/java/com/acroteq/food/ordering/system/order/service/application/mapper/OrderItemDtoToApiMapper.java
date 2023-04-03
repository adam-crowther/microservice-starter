package com.acroteq.food.ordering.system.order.service.application.mapper;

import com.acroteq.food.ordering.system.order.service.application.model.OrderItem;
import com.acroteq.food.ordering.system.order.service.domain.dto.create.OrderItemDto;
import org.mapstruct.Mapper;

@Mapper
public interface OrderItemDtoToApiMapper {

  OrderItem convertDtoToApi(OrderItemDto orderItem);
}
