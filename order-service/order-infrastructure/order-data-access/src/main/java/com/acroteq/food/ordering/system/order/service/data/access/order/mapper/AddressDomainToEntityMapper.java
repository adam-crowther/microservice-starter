package com.acroteq.food.ordering.system.order.service.data.access.order.mapper;

import com.acroteq.food.ordering.system.order.service.data.access.order.entity.OrderAddressEntity;
import com.acroteq.food.ordering.system.order.service.domain.valueobject.StreetAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AddressDomainToEntityMapper {

  @Mapping(target = "id", source = "address.id")
  @Mapping(target = "street", source = "address.street")
  @Mapping(target = "postalCode", source = "address.postalCode")
  @Mapping(target = "city", source = "address.city")
  @Mapping(target = "order", ignore = true)
  OrderAddressEntity convertDomainToEntity(StreetAddress address);
}
