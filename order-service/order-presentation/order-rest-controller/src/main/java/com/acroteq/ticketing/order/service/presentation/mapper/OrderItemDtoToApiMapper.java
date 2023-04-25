package com.acroteq.ticketing.order.service.presentation.mapper;

import com.acroteq.ticketing.order.service.domain.dto.create.OrderItemDto;
import com.acroteq.ticketing.order.service.presentation.model.OrderItem;
import org.mapstruct.Mapper;

@Mapper
public interface OrderItemDtoToApiMapper {

  OrderItem convertDtoToApi(OrderItemDto orderItem);
}
