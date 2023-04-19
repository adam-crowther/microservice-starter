package com.acroteq.ticketing.order.service.application.mapper;

import com.acroteq.ticketing.order.service.application.model.OrderItem;
import com.acroteq.ticketing.order.service.domain.dto.create.OrderItemDto;
import org.mapstruct.Mapper;

@Mapper
public interface OrderItemDtoToApiMapper {

  OrderItem convertDtoToApi(OrderItemDto orderItem);
}
