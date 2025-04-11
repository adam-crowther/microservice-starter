package com.acroteq.ticketing.order.service.presentation.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.common.application.mapper.EntityToApiMapper;
import com.acroteq.ticketing.order.service.domain.entity.Order;
import com.acroteq.ticketing.order.service.domain.mapper.order.TrackingIdMapper;
import com.acroteq.ticketing.order.service.presentation.model.CreateOrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfig.class, uses = { TrackingIdMapper.class, AuditMapper.class })
public interface CreateOrderResponseMapper extends EntityToApiMapper<Order, CreateOrderResponse> {

  @Mapping(target = "id", source = "id.value")
  @Mapping(target = "status", source = "orderStatus")
  @Override
  CreateOrderResponse convert(Order order);
}
