package com.acroteq.ticketing.order.service.presentation.mapper;

import com.acroteq.ticketing.common.application.mapper.ApiToDtoMapper;
import com.acroteq.ticketing.order.service.domain.dto.create.OrderAddressDto;
import com.acroteq.ticketing.order.service.presentation.model.CreateAddressCommand;
import org.mapstruct.Mapper;

@Mapper
public interface CreateAddressCommandApiToDtoMapper extends ApiToDtoMapper<CreateAddressCommand, OrderAddressDto> {

  @Override
  OrderAddressDto convertApiToDto(CreateAddressCommand createAddressCommand);
}
