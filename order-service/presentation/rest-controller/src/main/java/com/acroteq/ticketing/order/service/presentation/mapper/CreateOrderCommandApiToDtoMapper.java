package com.acroteq.ticketing.order.service.presentation.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.common.application.mapper.ApiToDtoMapper;
import com.acroteq.ticketing.order.service.domain.dto.create.CreateOrderCommandDto;
import com.acroteq.ticketing.order.service.presentation.model.CreateOrderCommand;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class)
public interface CreateOrderCommandApiToDtoMapper extends ApiToDtoMapper<CreateOrderCommand, CreateOrderCommandDto> {

  @Override
  CreateOrderCommandDto convertApiToDto(CreateOrderCommand createOrderCommand);
}
