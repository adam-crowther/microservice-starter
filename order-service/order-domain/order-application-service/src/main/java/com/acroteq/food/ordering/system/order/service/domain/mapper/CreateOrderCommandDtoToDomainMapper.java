package com.acroteq.food.ordering.system.order.service.domain.mapper;

import com.acroteq.food.ordering.system.common.application.mapper.IdMapper;
import com.acroteq.food.ordering.system.order.service.domain.dto.create.CreateOrderCommandDto;
import com.acroteq.food.ordering.system.order.service.domain.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { OrderItemDtoToDomainMapper.class,
                 IdMapper.class,
                 AddressDtoToDomainMapper.class })
public interface CreateOrderCommandDtoToDomainMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "streetAddress", source = "address")
  @Mapping(target = "trackingId", ignore = true)
  @Mapping(target = "orderStatus", ignore = true)
  @Mapping(target = "result", ignore = true)
  Order convertDtoToDomain(CreateOrderCommandDto createOrderCommand);
}
