package com.acroteq.ticketing.order.service.presentation.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.common.application.mapper.ApiToDtoMapper;
import com.acroteq.ticketing.order.service.domain.dto.create.OrderAddressDto;
import com.acroteq.ticketing.order.service.presentation.model.CreateAddressCommand;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class)
public interface CreateAddressCommandApiToDtoMapper extends ApiToDtoMapper<CreateAddressCommand, OrderAddressDto> {

  @Override
  OrderAddressDto convertApiToDto(CreateAddressCommand createAddressCommand);
}
