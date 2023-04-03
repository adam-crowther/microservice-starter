package com.acroteq.food.ordering.system.order.service.data.access.order.mapper;

import com.acroteq.food.ordering.system.common.application.mapper.IdMapper;
import com.acroteq.food.ordering.system.common.application.mapper.ValidationResultMapper;
import com.acroteq.food.ordering.system.domain.validation.ValidationResult;
import com.acroteq.food.ordering.system.order.service.data.access.order.entity.OrderEntity;
import com.acroteq.food.ordering.system.order.service.domain.entity.Order;
import com.acroteq.food.ordering.system.order.service.domain.valueobject.TrackingId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(uses = { IdMapper.class,
                 ValidationResultMapper.class,
                 OrderItemEntityToDomainMapper.class,
                 AddressEntityToDomainMapper.class },
        imports = ValidationResult.class)
public interface OrderEntityToDomainMapper {

  @Mapping(target = "streetAddress", source = "address")
  @Mapping(target = "result", source = "failureMessages")
  Order convertEntityToDomain(OrderEntity order);

  @Mapping(target = "value", source = "uuid")
  TrackingId convertUuidToTrackingId(UUID uuid);
}
