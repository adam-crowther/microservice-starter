package com.acroteq.food.ordering.system.order.service.domain.mapper;

import com.acroteq.food.ordering.system.common.application.mapper.IdMapper;
import com.acroteq.food.ordering.system.order.service.domain.dto.create.CreateOrderResponseDto;
import com.acroteq.food.ordering.system.order.service.domain.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { IdMapper.class })
public interface CreateOrderResponseDomainToDtoMapper {

  @Mapping(target = "trackingId", source = "order.trackingId")
  @Mapping(target = "status", source = "order.orderStatus")
  @Mapping(target = "message", source = "message")
  CreateOrderResponseDto convertDomainToDto(Order order, String message);
}
