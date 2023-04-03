package com.acroteq.food.ordering.system.order.service.data.access.order.mapper;

import com.acroteq.food.ordering.system.order.service.data.access.order.entity.OrderAddressEntity;
import com.acroteq.food.ordering.system.order.service.domain.valueobject.StreetAddress;
import org.mapstruct.Mapper;

@Mapper
public interface AddressEntityToDomainMapper {

  StreetAddress convertEntityToDomain(OrderAddressEntity address);
}
