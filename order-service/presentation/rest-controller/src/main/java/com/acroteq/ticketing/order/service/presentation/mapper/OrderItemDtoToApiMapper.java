package com.acroteq.ticketing.order.service.presentation.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.common.application.mapper.DtoToApiMapper;
import com.acroteq.ticketing.order.service.domain.dto.create.OrderItemDto;
import com.acroteq.ticketing.order.service.presentation.model.OrderItem;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class)
public interface OrderItemDtoToApiMapper extends DtoToApiMapper<OrderItemDto, OrderItem> {

  @Override
  OrderItem convertDtoToApi(OrderItemDto orderItem);
}
