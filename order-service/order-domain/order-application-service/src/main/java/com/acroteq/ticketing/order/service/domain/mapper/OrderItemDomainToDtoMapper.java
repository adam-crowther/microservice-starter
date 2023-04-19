package com.acroteq.ticketing.order.service.domain.mapper;

import com.acroteq.ticketing.application.mapper.FlightIdMapper;
import com.acroteq.ticketing.order.service.domain.dto.create.OrderItemDto;
import com.acroteq.ticketing.order.service.domain.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = FlightIdMapper.class)
public interface OrderItemDomainToDtoMapper {

  @Mapping(target = "flightId", source = "flight.id")
  OrderItemDto convertDomainToDto(OrderItem orderItems);
}
