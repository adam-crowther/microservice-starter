package com.acroteq.ticketing.order.service.data.access.order.mapper;

import com.acroteq.ticketing.application.mapper.AirlineIdMapper;
import com.acroteq.ticketing.application.mapper.CustomerIdMapper;
import com.acroteq.ticketing.application.mapper.OrderIdMapper;
import com.acroteq.ticketing.domain.valueobject.FlightId;
import com.acroteq.ticketing.domain.valueobject.OrderId;
import com.acroteq.ticketing.domain.valueobject.OrderItemId;
import com.acroteq.ticketing.order.service.data.access.order.entity.OrderItemJpaEntity;
import com.acroteq.ticketing.order.service.domain.entity.OrderItem;
import com.acroteq.ticketing.order.service.domain.mapper.TrackingIdMapper;
import com.acroteq.ticketing.order.service.domain.resolver.FlightResolver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { OrderIdMapper.class,
                 TrackingIdMapper.class,
                 CustomerIdMapper.class,
                 AirlineIdMapper.class,
                 FlightResolver.class }, imports = { OrderItemId.class, OrderId.class, FlightId.class })
public interface OrderItemJpaToDomainMapper {

  @Mapping(target = "id", source = "id")
  @Mapping(target = "flight", source = "flightId")
  OrderItem convertEntityToDomain(OrderItemJpaEntity orderItem);

  @Mapping(target = "value", source = "id")
  OrderItemId convertUuidToOrderItemId(Long id);
}
