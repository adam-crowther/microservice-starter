package com.acroteq.food.ordering.system.order.service.domain.mapper;

import com.acroteq.food.ordering.system.order.service.domain.dto.create.OrderAddressDto;
import com.acroteq.food.ordering.system.order.service.domain.valueobject.StreetAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(imports = {UUID.class})
public interface AddressDtoToDomainMapper {

  @Mapping(target = "id", expression = "java(UUID.randomUUID())")
  StreetAddress convertDtoToDomain(OrderAddressDto orderAddress);
}
