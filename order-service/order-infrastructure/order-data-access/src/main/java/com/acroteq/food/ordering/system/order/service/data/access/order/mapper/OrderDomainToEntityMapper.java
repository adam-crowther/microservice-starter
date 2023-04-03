package com.acroteq.food.ordering.system.order.service.data.access.order.mapper;

import com.acroteq.food.ordering.system.common.application.mapper.IdMapper;
import com.acroteq.food.ordering.system.common.application.mapper.ValidationResultMapper;
import com.acroteq.food.ordering.system.order.service.data.access.order.entity.OrderEntity;
import com.acroteq.food.ordering.system.order.service.domain.entity.Order;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(uses = { IdMapper.class,
                 ValidationResultMapper.class,
                 OrderItemDomainToEntityMapper.class,
                 AddressDomainToEntityMapper.class })
public abstract class OrderDomainToEntityMapper {

  @Mapping(target = "address", source = "streetAddress")
  @Mapping(target = "priceCurrencyId", source = "price.currencyId")
  @Mapping(target = "priceAmount", source = "price.amount")
  @Mapping(target = "failureMessages", source = "result")
  public abstract OrderEntity convertDomainToEntity(Order order);

  @AfterMapping
  void setOrderBackReferences(@MappingTarget final OrderEntity orderEntity) {
    orderEntity.getItems()
               .forEach(e -> e.setOrder(orderEntity));
    orderEntity.getAddress()
               .setOrder(orderEntity);
  }
}
