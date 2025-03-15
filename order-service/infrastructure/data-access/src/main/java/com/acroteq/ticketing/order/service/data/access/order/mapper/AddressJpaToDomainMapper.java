package com.acroteq.ticketing.order.service.data.access.order.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.ticketing.order.service.data.access.order.entity.OrderAddressJpaEmbedded;
import com.acroteq.ticketing.order.service.domain.valueobject.StreetAddress;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class)
public interface AddressJpaToDomainMapper {

  StreetAddress convertJpaToDomain(OrderAddressJpaEmbedded address);
}
