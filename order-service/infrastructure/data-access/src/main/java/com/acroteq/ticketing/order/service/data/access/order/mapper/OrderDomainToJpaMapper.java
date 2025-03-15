package com.acroteq.ticketing.order.service.data.access.order.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.ValidationResultMapper;
import com.acroteq.application.mapper.id.AirlineIdMapper;
import com.acroteq.application.mapper.id.CurrencyIdMapper;
import com.acroteq.application.mapper.id.CustomerIdMapper;
import com.acroteq.application.mapper.id.OrderIdMapper;
import com.acroteq.infrastructure.mapper.DomainToJpaMapper;
import com.acroteq.ticketing.order.service.data.access.order.entity.OrderJpaEntity;
import com.acroteq.ticketing.order.service.data.access.order.resolver.AirlineJpaResolver;
import com.acroteq.ticketing.order.service.data.access.order.resolver.CustomerJpaResolver;
import com.acroteq.ticketing.order.service.domain.entity.Order;
import com.acroteq.ticketing.order.service.domain.mapper.order.TrackingIdMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapstructConfig.class,
        uses = { OrderIdMapper.class,
                 TrackingIdMapper.class,
                 CurrencyIdMapper.class,
                 CustomerIdMapper.class,
                 AirlineIdMapper.class,
                 ValidationResultMapper.class,
                 AddressDomainToJpaMapper.class,
                 OrderItemDomainToJpaMapper.class,
                 CustomerJpaResolver.class,
                 AirlineJpaResolver.class })
public interface OrderDomainToJpaMapper extends DomainToJpaMapper<Order, OrderJpaEntity> {

  @Mapping(target = "audit", ignore = true)
  @Mapping(target = "airline", source = "airline.id")
  @Mapping(target = "customer", source = "customer.id")
  @Mapping(target = "address", source = "streetAddress")
  @Mapping(target = "priceCurrencyId", source = "price.currencyId")
  @Mapping(target = "priceAmount", source = "price.amount")
  @Mapping(target = "failureMessages", source = "result")
  @Override
  OrderJpaEntity convertDomainToJpa(Order entity);

  @Mapping(target = "audit", ignore = true)
  @Mapping(target = "airline", source = "airline.id")
  @Mapping(target = "customer", source = "customer.id")
  @Mapping(target = "address", source = "entity.streetAddress")
  @Mapping(target = "priceCurrencyId", source = "entity.price.currencyId")
  @Mapping(target = "priceAmount", source = "entity.price.amount")
  @Mapping(target = "failureMessages", source = "entity.result")
  @Override
  OrderJpaEntity convertDomainToJpa(Order entity, @MappingTarget OrderJpaEntity jpaEntity);
}
