package com.acroteq.ticketing.order.service.presentation.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.common.application.mapper.ApiToDtoMapper;
import com.acroteq.ticketing.order.service.domain.dto.create.OrderItemDto;
import com.acroteq.ticketing.order.service.presentation.model.CreateOrderItemCommand;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class)
public interface CreateOrderItemCommandApiToDtoMapper extends ApiToDtoMapper<CreateOrderItemCommand, OrderItemDto> {

  @Override
  OrderItemDto convertApiToDto(CreateOrderItemCommand createOrderItemCommand);
}
