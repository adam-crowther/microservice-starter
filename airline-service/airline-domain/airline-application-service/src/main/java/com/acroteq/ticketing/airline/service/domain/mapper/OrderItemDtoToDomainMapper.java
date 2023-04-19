package com.acroteq.ticketing.airline.service.domain.mapper;

import com.acroteq.ticketing.airline.service.domain.dto.OrderItemDto;
import com.acroteq.ticketing.airline.service.domain.entity.OrderItem;
import com.acroteq.ticketing.airline.service.domain.resolver.FlightResolver;
import com.acroteq.ticketing.application.mapper.OrderIdMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { OrderIdMapper.class, FlightResolver.class })
public interface OrderItemDtoToDomainMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "flight", source = "flightId")
  OrderItem convertDtoToDomain(OrderItemDto airlineApprovalRequest);
}
