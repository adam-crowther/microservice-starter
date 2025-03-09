package com.acroteq.application.mapper.id;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.ticketing.domain.valueobject.OrderId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(config = MapstructConfig.class, imports = UUID.class)
public interface OrderIdMapper extends IdMapper<OrderId> {

  @Mapping(target = "value", source = "id")
  @Override
  OrderId convertStringToId(String id);

  @Mapping(target = "value", source = "id")
  @Override
  OrderId convertLongToId(Long id);
}
