package com.acroteq.ticketing.order.service.domain.mapper;

import com.acroteq.ticketing.order.service.domain.dto.create.OrderAddressDto;
import com.acroteq.ticketing.order.service.domain.valueobject.StreetAddress;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(imports = { UUID.class })
public interface AddressDtoToDomainMapper {

  StreetAddress convertDtoToDomain(OrderAddressDto orderAddress);
}
