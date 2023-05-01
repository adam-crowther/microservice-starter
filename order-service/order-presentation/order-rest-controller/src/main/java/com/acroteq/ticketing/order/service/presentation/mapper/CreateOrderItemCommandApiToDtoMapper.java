package com.acroteq.ticketing.order.service.presentation.mapper;

import com.acroteq.ticketing.common.application.mapper.ApiToDtoMapper;
import com.acroteq.ticketing.order.service.domain.dto.create.OrderItemDto;
import com.acroteq.ticketing.order.service.presentation.model.CreateOrderItemCommand;
import org.mapstruct.Mapper;

@Mapper
public interface CreateOrderItemCommandApiToDtoMapper extends ApiToDtoMapper<CreateOrderItemCommand, OrderItemDto> {

  @Override
  OrderItemDto convertApiToDto(CreateOrderItemCommand createOrderItemCommand);
}
