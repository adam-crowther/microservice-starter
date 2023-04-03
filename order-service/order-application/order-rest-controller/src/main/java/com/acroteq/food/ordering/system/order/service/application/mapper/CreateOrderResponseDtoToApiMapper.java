package com.acroteq.food.ordering.system.order.service.application.mapper;

import com.acroteq.food.ordering.system.order.service.application.model.CreateOrderResponse;
import com.acroteq.food.ordering.system.order.service.domain.dto.create.CreateOrderResponseDto;
import org.mapstruct.Mapper;

@Mapper(uses = { OrderAddressDtoToApiMapper.class })
public interface CreateOrderResponseDtoToApiMapper {

  CreateOrderResponse convertDtoToApi(CreateOrderResponseDto responseDto);
}
