package com.acroteq.ticketing.order.service.presentation.mapper;

import com.acroteq.ticketing.common.application.mapper.ApiToDtoMapper;
import com.acroteq.ticketing.order.service.domain.dto.create.CreateOrderCommandDto;
import com.acroteq.ticketing.order.service.presentation.model.CreateOrderCommand;
import org.mapstruct.Mapper;

@Mapper
public interface CreateOrderCommandApiToDtoMapper extends ApiToDtoMapper<CreateOrderCommand, CreateOrderCommandDto> {

  @Override
  CreateOrderCommandDto convertApiToDto(CreateOrderCommand createOrderCommand);
}
