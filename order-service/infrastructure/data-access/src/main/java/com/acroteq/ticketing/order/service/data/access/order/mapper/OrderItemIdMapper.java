package com.acroteq.ticketing.order.service.data.access.order.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.IdMapper;
import com.acroteq.domain.valueobject.OrderItemId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfig.class)
public interface OrderItemIdMapper extends IdMapper<OrderItemId> {

  @Mapping(target = "value", expression = "java(Long.parseLong(id))")
  @Override
  OrderItemId convertStringToId(String id);

  @Mapping(target = "value", source = "id")
  @Override
  OrderItemId convertLongToId(Long id);
}
