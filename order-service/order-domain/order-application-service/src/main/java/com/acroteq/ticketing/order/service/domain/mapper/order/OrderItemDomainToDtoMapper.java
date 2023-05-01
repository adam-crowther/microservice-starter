package com.acroteq.ticketing.order.service.domain.mapper.order;

import com.acroteq.ticketing.application.mapper.DomainToDtoMapper;
import com.acroteq.ticketing.application.mapper.id.FlightIdMapper;
import com.acroteq.ticketing.order.service.domain.dto.create.OrderItemDto;
import com.acroteq.ticketing.order.service.domain.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = FlightIdMapper.class)
public interface OrderItemDomainToDtoMapper extends DomainToDtoMapper<OrderItem, OrderItemDto> {

  @Mapping(target = "flightId", source = "flight.id")
  @Override
  OrderItemDto convertDomainToDto(OrderItem orderItems);
}
