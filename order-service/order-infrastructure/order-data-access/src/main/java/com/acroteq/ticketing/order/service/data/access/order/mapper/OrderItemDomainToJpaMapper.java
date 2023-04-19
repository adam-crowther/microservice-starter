package com.acroteq.ticketing.order.service.data.access.order.mapper;

import com.acroteq.ticketing.application.mapper.AirlineIdMapper;
import com.acroteq.ticketing.application.mapper.CurrencyIdMapper;
import com.acroteq.ticketing.application.mapper.CustomerIdMapper;
import com.acroteq.ticketing.application.mapper.FlightIdMapper;
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
                 OrderItemIdMapper.class,
                 TrackingIdMapper.class,
                 CurrencyIdMapper.class,
                 CustomerIdMapper.class,
                 FlightIdMapper.class,
                 AirlineIdMapper.class,
                 FlightResolver.class }, imports = { OrderItemId.class, OrderId.class, FlightId.class })
public interface OrderItemDomainToJpaMapper {

  @Mapping(target = "id", source = "orderItem.id")
  @Mapping(target = "orderId", ignore = true)
  @Mapping(target = "flightId", source = "orderItem.flight.id")
  OrderItemJpaEntity convertDomainToEntity(OrderItem orderItem);
}
