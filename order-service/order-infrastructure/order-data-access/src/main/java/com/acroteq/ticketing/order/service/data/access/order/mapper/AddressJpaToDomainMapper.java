package com.acroteq.ticketing.order.service.data.access.order.mapper;

import com.acroteq.ticketing.order.service.data.access.order.entity.OrderAddressJpaEntity;
import com.acroteq.ticketing.order.service.domain.valueobject.StreetAddress;
import org.mapstruct.Mapper;

@Mapper
public interface AddressJpaToDomainMapper {

  StreetAddress convertEntityToJpa(OrderAddressJpaEntity address);
}
