package com.acroteq.ticketing.order.service.data.access.order.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.ticketing.order.service.data.access.order.entity.OrderAddressJpaEmbedded;
import com.acroteq.ticketing.order.service.domain.valueobject.StreetAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfig.class)
public interface AddressJpaMapper {

  StreetAddress convert(OrderAddressJpaEmbedded address);

  @Mapping(target = "street", source = "address.street")
  @Mapping(target = "postalCode", source = "address.postalCode")
  @Mapping(target = "city", source = "address.city")
  OrderAddressJpaEmbedded convert(StreetAddress address);
}
