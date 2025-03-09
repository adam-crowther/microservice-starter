package com.acroteq.ticketing.order.service.presentation.mapper;

import com.acroteq.ticketing.application.mapper.MapstructConfig;
import com.acroteq.ticketing.common.application.mapper.DtoToApiMapper;
import com.acroteq.ticketing.order.service.domain.dto.create.CreateOrderResponseDto;
import com.acroteq.ticketing.order.service.presentation.model.CreateOrderResponse;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class, uses = { OrderAddressDtoToApiMapper.class, AuditDtoToApiMapper.class })
public interface CreateOrderResponseDtoToApiMapper extends DtoToApiMapper<CreateOrderResponseDto, CreateOrderResponse> {

  @Override
  CreateOrderResponse convertDtoToApi(CreateOrderResponseDto responseDto);
}
