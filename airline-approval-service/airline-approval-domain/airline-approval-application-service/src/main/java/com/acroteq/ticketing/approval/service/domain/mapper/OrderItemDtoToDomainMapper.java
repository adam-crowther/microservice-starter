package com.acroteq.ticketing.approval.service.domain.mapper;

import com.acroteq.ticketing.application.mapper.OrderIdMapper;
import com.acroteq.ticketing.approval.service.domain.dto.OrderItemDto;
import com.acroteq.ticketing.approval.service.domain.entity.order.OrderItem;
import com.acroteq.ticketing.approval.service.domain.resolver.FlightResolver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { OrderIdMapper.class, FlightResolver.class })
public interface OrderItemDtoToDomainMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "flight", source = "flightId")
  OrderItem convertDtoToDomain(OrderItemDto airlineApprovalRequest);
}
