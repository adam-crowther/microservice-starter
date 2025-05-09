package com.acroteq.ticketing.order.service.data.access.order.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.ValidationResultMapper;
import com.acroteq.application.mapper.id.AirlineIdMapper;
import com.acroteq.application.mapper.id.CurrencyIdMapper;
import com.acroteq.application.mapper.id.CustomerIdMapper;
import com.acroteq.application.mapper.id.OrderIdMapper;
import com.acroteq.infrastructure.mapper.JpaMapper;
import com.acroteq.mapstruct.Resolve;
import com.acroteq.ticketing.order.service.data.access.airline.mapper.AirlineJpaMapper;
import com.acroteq.ticketing.order.service.data.access.customer.mapper.CustomerJpaMapper;
import com.acroteq.ticketing.order.service.data.access.order.entity.OrderJpaEntity;
import com.acroteq.ticketing.order.service.data.access.order.repository.OrderJpaRepository;
import com.acroteq.ticketing.order.service.domain.entity.Order;
import com.acroteq.ticketing.order.service.domain.mapper.order.TrackingIdMapper;
import lombok.Getter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(config = MapstructConfig.class,
        uses = { OrderIdMapper.class,
                 TrackingIdMapper.class,
                 CurrencyIdMapper.class,
                 CustomerIdMapper.class,
                 AirlineIdMapper.class,
                 ValidationResultMapper.class,
                 AddressJpaMapper.class,
                 OrderItemJpaMapper.class,
                 CustomerJpaMapper.class,
                 AirlineJpaMapper.class })
public abstract class OrderJpaMapper implements JpaMapper<Order, OrderJpaEntity> {

  @Getter
  @Autowired
  private OrderJpaRepository repository;

  @Mapping(target = "streetAddress", source = "address")
  @Mapping(target = "result", source = "failureMessages")
  @Override
  public abstract Order convert(OrderJpaEntity order);

  @Mapping(target = "audit", ignore = true)
  @Mapping(target = "airline", qualifiedBy = Resolve.class)
  @Mapping(target = "customer", qualifiedBy = Resolve.class)
  @Mapping(target = "address", source = "streetAddress")
  @Mapping(target = "priceCurrencyId", source = "price.currencyId")
  @Mapping(target = "priceAmount", source = "price.amount")
  @Mapping(target = "failureMessages", source = "result")
  @Override
  public abstract OrderJpaEntity convertNew(Order entity);

  @Mapping(target = "audit", ignore = true)
  @Mapping(target = "airline", qualifiedBy = Resolve.class)
  @Mapping(target = "customer", qualifiedBy = Resolve.class)
  @Mapping(target = "address", source = "entity.streetAddress")
  @Mapping(target = "priceCurrencyId", source = "entity.price.currencyId")
  @Mapping(target = "priceAmount", source = "entity.price.amount")
  @Mapping(target = "failureMessages", source = "entity.result")
  @Override
  public abstract OrderJpaEntity convertExisting(Order entity, @MappingTarget OrderJpaEntity jpaEntity);
}
