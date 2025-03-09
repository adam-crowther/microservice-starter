package com.acroteq.ticketing.order.service.domain.mapper.order;

import com.acroteq.ticketing.application.mapper.DomainToDtoMapper;
import com.acroteq.ticketing.application.mapper.MapstructConfig;
import com.acroteq.ticketing.order.service.domain.dto.create.OrderAddressDto;
import com.acroteq.ticketing.order.service.domain.valueobject.StreetAddress;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(config = MapstructConfig.class, imports = { UUID.class })
public interface AddressDomainToDtoMapper extends DomainToDtoMapper<StreetAddress, OrderAddressDto> {

  @Override
  OrderAddressDto convertDomainToDto(StreetAddress orderAddress);
}
