package com.acroteq.food.ordering.system.order.service.application.mapper;

import com.acroteq.food.ordering.system.order.service.application.model.CreateOrderCommand;
import com.acroteq.food.ordering.system.order.service.domain.dto.create.CreateOrderCommandDto;
import org.mapstruct.Mapper;

@Mapper
public interface CreateOrderCommandApiToDtoMapper {

  CreateOrderCommandDto convertApiToDto(CreateOrderCommand createOrderCommand);
}
