package com.acroteq.ticketing.order.service.data.access.order.mapper;

import com.acroteq.ticketing.application.mapper.id.AirlineIdMapper;
import com.acroteq.ticketing.application.mapper.id.CustomerIdMapper;
import com.acroteq.ticketing.application.mapper.id.OrderIdMapper;
import com.acroteq.ticketing.domain.valueobject.FlightId;
import com.acroteq.ticketing.domain.valueobject.OrderId;
import com.acroteq.ticketing.domain.valueobject.OrderItemId;
import com.acroteq.ticketing.infrastructure.mapper.JpaToDomainMapper;
import com.acroteq.ticketing.order.service.data.access.order.entity.OrderItemJpaEntity;
import com.acroteq.ticketing.order.service.domain.entity.OrderItem;
import com.acroteq.ticketing.order.service.domain.mapper.order.TrackingIdMapper;
import com.acroteq.ticketing.order.service.domain.resolver.FlightResolver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { OrderIdMapper.class,
                 OrderItemIdMapper.class,
                 TrackingIdMapper.class,
                 CustomerIdMapper.class,
                 AirlineIdMapper.class,
                 FlightResolver.class }, imports = { OrderItemId.class, OrderId.class, FlightId.class })
public interface OrderItemJpaToDomainMapper extends JpaToDomainMapper<OrderItemJpaEntity, OrderItem> {

  @Mapping(target = "id", source = "id")
  @Mapping(target = "flight", source = "flightId")
  @Override
  OrderItem convertJpaToDomain(OrderItemJpaEntity orderItem);
}
