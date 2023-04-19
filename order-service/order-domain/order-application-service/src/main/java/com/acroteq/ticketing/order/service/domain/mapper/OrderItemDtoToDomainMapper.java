package com.acroteq.ticketing.order.service.domain.mapper;

import com.acroteq.ticketing.application.mapper.OrderIdMapper;
import com.acroteq.ticketing.order.service.domain.dto.create.OrderItemDto;
import com.acroteq.ticketing.order.service.domain.entity.OrderItem;
import com.acroteq.ticketing.order.service.domain.resolver.FlightResolver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(uses = { OrderIdMapper.class, FlightResolver.class })
public interface OrderItemDtoToDomainMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "orderId", ignore = true)
  @Mapping(target = "flight", source = "flightId")
  OrderItem convertDtoToDomain(OrderItemDto orderItems);

  List<OrderItem> convertDtoToDomain(List<OrderItemDto> orderItems);
}
