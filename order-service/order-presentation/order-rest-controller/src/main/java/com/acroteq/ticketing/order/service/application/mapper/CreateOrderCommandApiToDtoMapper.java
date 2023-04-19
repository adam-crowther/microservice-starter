package com.acroteq.ticketing.order.service.application.mapper;

import com.acroteq.ticketing.order.service.application.model.CreateOrderCommand;
import com.acroteq.ticketing.order.service.domain.dto.create.CreateOrderCommandDto;
import org.mapstruct.Mapper;

@Mapper
public interface CreateOrderCommandApiToDtoMapper {

  CreateOrderCommandDto convertApiToDto(CreateOrderCommand createOrderCommand);
}
