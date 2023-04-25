package com.acroteq.ticketing.order.service.presentation.mapper;

import com.acroteq.ticketing.order.service.domain.dto.track.TrackOrderResponseDto;
import com.acroteq.ticketing.order.service.presentation.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { OrderAddressDtoToApiMapper.class, OrderItemDtoToApiMapper.class })
public interface TrackOrderResponseDtoToApiMapper {

  @Mapping(target = "address", source = "streetAddress")
  @Mapping(target = "status", source = "orderStatus")
  Order convertDtoToApi(TrackOrderResponseDto responseDto);
}
