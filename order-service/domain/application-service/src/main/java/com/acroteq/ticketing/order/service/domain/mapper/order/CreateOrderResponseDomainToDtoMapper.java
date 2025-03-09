package com.acroteq.ticketing.order.service.domain.mapper.order;

import com.acroteq.ticketing.application.mapper.DomainToDtoMapper;
import com.acroteq.ticketing.application.mapper.MapstructConfig;
import com.acroteq.ticketing.application.mapper.id.OrderIdMapper;
import com.acroteq.ticketing.order.service.domain.dto.create.CreateOrderResponseDto;
import com.acroteq.ticketing.order.service.domain.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfig.class, uses = { OrderIdMapper.class, TrackingIdMapper.class })
public interface CreateOrderResponseDomainToDtoMapper extends DomainToDtoMapper<Order, CreateOrderResponseDto> {

  @Mapping(target = "trackingId", source = "order.trackingId")
  @Mapping(target = "status", source = "order.orderStatus")
  @Mapping(target = "message", ignore = true)
  @Override
  CreateOrderResponseDto convertDomainToDto(Order order);

  @Mapping(target = "trackingId", source = "order.trackingId")
  @Mapping(target = "status", source = "order.orderStatus")
  @Mapping(target = "message", source = "message")
  CreateOrderResponseDto convertDomainToDto(Order order, String message);
}
