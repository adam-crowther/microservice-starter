package com.acroteq.ticketing.order.service.data.access.order.mapper;

import com.acroteq.ticketing.application.mapper.id.AirlineIdMapper;
import com.acroteq.ticketing.application.mapper.id.CurrencyIdMapper;
import com.acroteq.ticketing.application.mapper.id.CustomerIdMapper;
import com.acroteq.ticketing.application.mapper.id.FlightIdMapper;
import com.acroteq.ticketing.application.mapper.id.OrderIdMapper;
import com.acroteq.ticketing.domain.valueobject.FlightId;
import com.acroteq.ticketing.domain.valueobject.OrderId;
import com.acroteq.ticketing.domain.valueobject.OrderItemId;
import com.acroteq.ticketing.infrastructure.mapper.DomainToJpaMapper;
import com.acroteq.ticketing.order.service.data.access.order.entity.OrderItemJpaEntity;
import com.acroteq.ticketing.order.service.domain.entity.OrderItem;
import com.acroteq.ticketing.order.service.domain.mapper.order.TrackingIdMapper;
import com.acroteq.ticketing.order.service.domain.resolver.FlightResolver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(uses = { OrderIdMapper.class,
                 OrderItemIdMapper.class,
                 TrackingIdMapper.class,
                 CurrencyIdMapper.class,
                 CustomerIdMapper.class,
                 FlightIdMapper.class,
                 AirlineIdMapper.class,
                 FlightResolver.class }, imports = { OrderItemId.class, OrderId.class, FlightId.class })
public interface OrderItemDomainToJpaMapper extends DomainToJpaMapper<OrderItem, OrderItemJpaEntity> {

  @Mapping(target = "audit", ignore = true)
  @Mapping(target = "id", source = "entity.id")
  @Mapping(target = "orderId", ignore = true)
  @Mapping(target = "flightId", source = "entity.flight.id")
  @Override
  OrderItemJpaEntity convertDomainToJpa(OrderItem entity);

  @Mapping(target = "audit", ignore = true)
  // @Mapping(target = "id", ignore = true)
  @Mapping(target = "orderId", ignore = true)
  @Mapping(target = "flightId", source = "entity.flight.id")
  @Override
  OrderItemJpaEntity convertDomainToJpa(OrderItem entity, @MappingTarget OrderItemJpaEntity jpaEntity);
}
