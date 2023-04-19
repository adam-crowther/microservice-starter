package com.acroteq.ticketing.order.service.domain.mapper;

import com.acroteq.ticketing.application.mapper.OrderIdMapper;
import com.acroteq.ticketing.order.service.domain.dto.create.CreateOrderResponseDto;
import com.acroteq.ticketing.order.service.domain.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { OrderIdMapper.class, TrackingIdMapper.class })
public interface CreateOrderResponseDomainToDtoMapper {

  @Mapping(target = "trackingId", source = "order.trackingId")
  @Mapping(target = "status", source = "order.orderStatus")
  @Mapping(target = "message", source = "message")
  CreateOrderResponseDto convertDomainToDto(Order order, String message);
}
