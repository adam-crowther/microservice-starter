package com.acroteq.ticketing.order.service.data.access.order.mapper;

import com.acroteq.ticketing.application.mapper.AirlineIdMapper;
import com.acroteq.ticketing.application.mapper.CurrencyIdMapper;
import com.acroteq.ticketing.application.mapper.CustomerIdMapper;
import com.acroteq.ticketing.application.mapper.OrderIdMapper;
import com.acroteq.ticketing.application.mapper.ValidationResultMapper;
import com.acroteq.ticketing.order.service.data.access.order.entity.OrderJpaEntity;
import com.acroteq.ticketing.order.service.domain.entity.Order;
import com.acroteq.ticketing.order.service.domain.mapper.TrackingIdMapper;
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
public abstract class OrderDomainToJpaMapper {

  @Mapping(target = "address", source = "streetAddress")
  @Mapping(target = "priceCurrencyId", source = "price.currencyId")
  @Mapping(target = "priceAmount", source = "price.amount")
  @Mapping(target = "failureMessages", source = "result")
  public abstract OrderJpaEntity convertDomainToEntity(Order order);

  @AfterMapping
  void setOrderBackReferences(@MappingTarget final OrderJpaEntity orderJpaEntity) {
    orderJpaEntity.getItems()
                  .forEach(entity -> entity.setOrderId(orderJpaEntity.getId()));
  }
}
