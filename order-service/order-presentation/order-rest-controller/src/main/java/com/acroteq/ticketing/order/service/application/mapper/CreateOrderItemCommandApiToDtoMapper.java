package com.acroteq.ticketing.order.service.application.mapper;

import com.acroteq.ticketing.order.service.application.model.CreateOrderItemCommand;
import com.acroteq.ticketing.order.service.domain.dto.create.OrderItemDto;
import org.mapstruct.Mapper;

@Mapper
public interface CreateOrderItemCommandApiToDtoMapper {

  OrderItemDto convertApiToDto(CreateOrderItemCommand createOrderItemCommand);
}
