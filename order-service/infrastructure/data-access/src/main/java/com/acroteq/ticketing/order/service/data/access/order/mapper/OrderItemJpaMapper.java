package com.acroteq.ticketing.order.service.data.access.order.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.AirlineIdMapper;
import com.acroteq.application.mapper.id.CurrencyIdMapper;
import com.acroteq.application.mapper.id.CustomerIdMapper;
import com.acroteq.application.mapper.id.FlightIdMapper;
import com.acroteq.application.mapper.id.OrderIdMapper;
import com.acroteq.domain.valueobject.FlightId;
import com.acroteq.domain.valueobject.OrderId;
import com.acroteq.domain.valueobject.OrderItemId;
import com.acroteq.infrastructure.mapper.JpaMapper;
import com.acroteq.mapstruct.Resolve;
import com.acroteq.ticketing.order.service.data.access.airline.mapper.FlightJpaMapper;
import com.acroteq.ticketing.order.service.data.access.order.entity.OrderItemJpaEntity;
import com.acroteq.ticketing.order.service.data.access.order.repository.OrderItemJpaRepository;
import com.acroteq.ticketing.order.service.domain.entity.OrderItem;
import com.acroteq.ticketing.order.service.domain.mapper.order.TrackingIdMapper;
import lombok.Getter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(config = MapstructConfig.class,
        uses = { OrderIdMapper.class,
                 OrderItemIdMapper.class,
                 TrackingIdMapper.class,
                 CurrencyIdMapper.class,
                 CustomerIdMapper.class,
                 FlightIdMapper.class,
                 AirlineIdMapper.class,
                 FlightJpaMapper.class },
        imports = { OrderItemId.class, OrderId.class, FlightId.class })
public abstract class OrderItemJpaMapper implements JpaMapper<OrderItem, OrderItemJpaEntity> {

  @Getter
  @Autowired
  private OrderItemJpaRepository repository;

  @Override
  public abstract OrderItem convert(OrderItemJpaEntity orderItem);

  @Mapping(target = "audit", ignore = true)
  @Mapping(target = "flight", qualifiedBy = Resolve.class)
  @Override
  public abstract OrderItemJpaEntity convertNew(OrderItem entity);

  @Mapping(target = "audit", ignore = true)
  @Mapping(target = "flight", qualifiedBy = Resolve.class)
  @Override
  public abstract OrderItemJpaEntity convertExisting(OrderItem entity, @MappingTarget OrderItemJpaEntity jpaEntity);
}
