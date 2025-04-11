package com.acroteq.ticketing.order.service.presentation.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.domain.valueobject.OrderStatus;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class)
public class OrderStatusMapper {

  public com.acroteq.ticketing.order.service.presentation.model.OrderStatus convert(final OrderStatus orderStatus) {
    return com.acroteq.ticketing.order.service.presentation.model.OrderStatus.valueOf(orderStatus.name());
  }

  public OrderStatus convert(final com.acroteq.ticketing.order.service.presentation.model.OrderStatus orderStatus) {
    return OrderStatus.valueOf(orderStatus.name());
  }
}
