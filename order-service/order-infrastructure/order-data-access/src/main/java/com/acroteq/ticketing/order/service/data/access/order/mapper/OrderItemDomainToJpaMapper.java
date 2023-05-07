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
import com.acroteq.ticketing.order.service.data.access.airline.mapper.FlightDomainToJpaMapper;
import com.acroteq.ticketing.order.service.data.access.order.entity.OrderItemJpaEntity;
import com.acroteq.ticketing.order.service.domain.entity.OrderItem;
import com.acroteq.ticketing.order.service.domain.mapper.order.TrackingIdMapper;
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
                 FlightDomainToJpaMapper.class }, imports = { OrderItemId.class, OrderId.class, FlightId.class })
public interface OrderItemDomainToJpaMapper extends DomainToJpaMapper<OrderItem, OrderItemJpaEntity> {

  @Mapping(target = "audit", ignore = true)
  @Override
  OrderItemJpaEntity convertDomainToJpa(OrderItem entity);

  @Mapping(target = "audit", ignore = true)
  @Mapping(target = "flight", source = "entity.flight")
  @Override
  OrderItemJpaEntity convertDomainToJpa(OrderItem entity, @MappingTarget OrderItemJpaEntity jpaEntity);
}
