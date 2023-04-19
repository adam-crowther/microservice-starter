package com.acroteq.ticketing.order.service.application.mapper;

import com.acroteq.ticketing.order.service.application.model.CreateOrderResponse;
import com.acroteq.ticketing.order.service.domain.dto.create.CreateOrderResponseDto;
import org.mapstruct.Mapper;

@Mapper(uses = { OrderAddressDtoToApiMapper.class })
public interface CreateOrderResponseDtoToApiMapper {

  CreateOrderResponse convertDtoToApi(CreateOrderResponseDto responseDto);
}
