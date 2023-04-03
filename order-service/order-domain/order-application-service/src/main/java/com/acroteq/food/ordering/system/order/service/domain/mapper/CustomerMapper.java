package com.acroteq.food.ordering.system.order.service.domain.mapper;

import com.acroteq.food.ordering.system.domain.valueobject.CustomerId;
import com.acroteq.food.ordering.system.domain.valueobject.Money;
import com.acroteq.food.ordering.system.order.service.domain.dto.common.CashValue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper
public interface CustomerMapper {

  @Mapping(target = "id", source = "uuid")
  CustomerId convert(UUID uuid);
}
