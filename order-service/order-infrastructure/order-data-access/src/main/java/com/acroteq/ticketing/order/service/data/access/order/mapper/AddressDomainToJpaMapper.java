package com.acroteq.ticketing.order.service.data.access.order.mapper;

import com.acroteq.ticketing.infrastructure.mapper.DomainToJpaMapper;
import com.acroteq.ticketing.order.service.data.access.order.entity.OrderAddressJpaEntity;
import com.acroteq.ticketing.order.service.domain.valueobject.StreetAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AddressDomainToJpaMapper extends DomainToJpaMapper<StreetAddress, OrderAddressJpaEntity> {

  @Mapping(target = "street", source = "address.street")
  @Mapping(target = "postalCode", source = "address.postalCode")
  @Mapping(target = "city", source = "address.city")
  @Override
  OrderAddressJpaEntity convertDomainToJpa(StreetAddress address);
}
