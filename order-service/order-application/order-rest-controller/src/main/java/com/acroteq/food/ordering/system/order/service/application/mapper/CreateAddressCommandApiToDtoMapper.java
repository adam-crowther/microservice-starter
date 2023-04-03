package com.acroteq.food.ordering.system.order.service.application.mapper;

import com.acroteq.food.ordering.system.order.service.application.model.CreateAddressCommand;
import com.acroteq.food.ordering.system.order.service.domain.dto.create.OrderAddressDto;
import org.mapstruct.Mapper;

@Mapper
public interface CreateAddressCommandApiToDtoMapper {

  OrderAddressDto convertApiToDto(CreateAddressCommand createAddressCommand);
}
