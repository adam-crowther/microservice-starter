package com.acroteq.food.ordering.system.order.service.domain.mapper;

import com.acroteq.food.ordering.system.order.service.domain.dto.create.OrderAddress;
import com.acroteq.food.ordering.system.order.service.domain.entity.Order;
import com.acroteq.food.ordering.system.order.service.domain.valueobject.StreetAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(imports = {UUID.class})
public interface StreetAddressMapper {

  @Mapping(target = "id", expression = "java(UUID.randomUUID())")
  StreetAddress convert(OrderAddress orderAddress);
}
