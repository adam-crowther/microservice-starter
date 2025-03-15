package com.acroteq.ticketing.order.service.domain.mapper.order;

import com.acroteq.application.mapper.DtoToDomainMapper;
import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.ticketing.order.service.domain.dto.create.OrderAddressDto;
import com.acroteq.ticketing.order.service.domain.valueobject.StreetAddress;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(config = MapstructConfig.class, imports = { UUID.class })
public interface AddressDtoToDomainMapper extends DtoToDomainMapper<OrderAddressDto, StreetAddress> {

  @Override
  StreetAddress convertDtoToDomain(OrderAddressDto orderAddress);
}
