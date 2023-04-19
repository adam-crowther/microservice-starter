package com.acroteq.ticketing.application.mapper;

import com.acroteq.ticketing.domain.valueobject.OrderId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(imports = UUID.class)
public interface OrderIdMapper extends IdMapper<OrderId> {

  @Mapping(target = "value", source = "id")
  @Override
  OrderId convertStringToId(String id);

  @Mapping(target = "value", source = "id")
  @Override
  OrderId convertLongToId(Long id);
}
