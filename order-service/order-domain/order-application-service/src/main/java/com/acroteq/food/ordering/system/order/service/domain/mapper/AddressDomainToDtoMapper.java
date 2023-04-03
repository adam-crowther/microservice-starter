package com.acroteq.food.ordering.system.order.service.domain.mapper;

import com.acroteq.food.ordering.system.order.service.domain.dto.create.OrderAddressDto;
import com.acroteq.food.ordering.system.order.service.domain.valueobject.StreetAddress;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(imports = {UUID.class})
public interface AddressDomainToDtoMapper {

  OrderAddressDto convertDomainToDto(StreetAddress orderAddress);
}
