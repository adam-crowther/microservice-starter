package com.acroteq.ticketing.order.service.data.access.order.mapper;

import com.acroteq.ticketing.application.mapper.ValidationResultMapper;
import com.acroteq.ticketing.application.mapper.id.AirlineIdMapper;
import com.acroteq.ticketing.application.mapper.id.CurrencyIdMapper;
import com.acroteq.ticketing.application.mapper.id.CustomerIdMapper;
import com.acroteq.ticketing.application.mapper.id.OrderIdMapper;
import com.acroteq.ticketing.infrastructure.mapper.DomainToJpaMapper;
import com.acroteq.ticketing.order.service.data.access.order.entity.OrderJpaEntity;
import com.acroteq.ticketing.order.service.domain.entity.Order;
import com.acroteq.ticketing.order.service.domain.mapper.order.TrackingIdMapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(uses = { OrderIdMapper.class,
                 TrackingIdMapper.class,
                 CurrencyIdMapper.class,
                 CustomerIdMapper.class,
                 AirlineIdMapper.class,
                 ValidationResultMapper.class,
                 OrderItemDomainToJpaMapper.class,
                 AddressDomainToJpaMapper.class })
public abstract class OrderDomainToJpaMapper implements DomainToJpaMapper<Order, OrderJpaEntity> {

  @Mapping(target = "address", source = "streetAddress")
  @Mapping(target = "priceCurrencyId", source = "price.currencyId")
  @Mapping(target = "priceAmount", source = "price.amount")
  @Mapping(target = "failureMessages", source = "result")
  @Override
  public abstract OrderJpaEntity convertDomainToJpa(Order order);

  @AfterMapping
  void setOrderBackReferences(@MappingTarget final OrderJpaEntity orderJpaEntity) {
    orderJpaEntity.getItems()
                  .forEach(entity -> entity.setOrderId(orderJpaEntity.getId()));
  }
}
