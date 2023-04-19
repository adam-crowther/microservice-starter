package com.acroteq.ticketing.order.service.application.mapper;

import com.acroteq.ticketing.order.service.application.model.Order;
import com.acroteq.ticketing.order.service.domain.dto.track.TrackOrderResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { OrderAddressDtoToApiMapper.class, OrderItemDtoToApiMapper.class })
public interface TrackOrderResponseDtoToApiMapper {

  @Mapping(target = "address", source = "streetAddress")
  Order convertDtoToApi(TrackOrderResponseDto responseDto);
}
