package com.acroteq.ticketing.order.service.domain.mapper.order;

import com.acroteq.ticketing.application.mapper.DtoToDomainMapper;
import com.acroteq.ticketing.application.mapper.MapstructConfig;
import com.acroteq.ticketing.application.mapper.id.OrderIdMapper;
import com.acroteq.ticketing.order.service.domain.dto.create.OrderItemDto;
import com.acroteq.ticketing.order.service.domain.entity.OrderItem;
import com.acroteq.ticketing.order.service.domain.resolver.FlightResolver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfig.class, uses = { OrderIdMapper.class, FlightResolver.class })
public interface OrderItemDtoToDomainMapper extends DtoToDomainMapper<OrderItemDto, OrderItem> {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "version", ignore = true)
  @Mapping(target = "flight", source = "flightId")
  @Override
  OrderItem convertDtoToDomain(OrderItemDto orderItems);

  List<OrderItem> convertDtoToDomain(List<OrderItemDto> orderItems);
}
