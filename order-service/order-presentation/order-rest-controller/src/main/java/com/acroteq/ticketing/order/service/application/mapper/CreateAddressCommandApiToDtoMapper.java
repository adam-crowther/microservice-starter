package com.acroteq.ticketing.order.service.application.mapper;

import com.acroteq.ticketing.order.service.application.model.CreateAddressCommand;
import com.acroteq.ticketing.order.service.domain.dto.create.OrderAddressDto;
import org.mapstruct.Mapper;

@Mapper
public interface CreateAddressCommandApiToDtoMapper {

  OrderAddressDto convertApiToDto(CreateAddressCommand createAddressCommand);
}
