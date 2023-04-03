package com.acroteq.food.ordering.system.order.service.application.mapper;

import com.acroteq.food.ordering.system.order.service.application.model.CreateOrderItemCommand;
import com.acroteq.food.ordering.system.order.service.domain.dto.create.OrderItemDto;
import org.mapstruct.Mapper;

@Mapper
public interface CreateOrderItemCommandApiToDtoMapper {

  OrderItemDto convertApiToDto(CreateOrderItemCommand createOrderItemCommand);
}
