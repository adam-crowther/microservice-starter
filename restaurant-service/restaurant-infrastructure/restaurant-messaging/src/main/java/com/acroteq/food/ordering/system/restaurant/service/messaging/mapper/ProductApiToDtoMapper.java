package com.acroteq.food.ordering.system.restaurant.service.messaging.mapper;

import com.acroteq.food.ordering.system.common.application.mapper.IdMapper;
import com.acroteq.food.ordering.system.restaurant.service.domain.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { IdMapper.class })
public interface ProductApiToDtoMapper {

  @Mapping(target = "name", ignore = true)
  @Mapping(target = "price", ignore = true)
  @Mapping(target = "available", ignore = true)
  Product convertApiToDto(com.acroteq.food.ordering.system.kafka.order.avro.model.Product requestMessage);
}
