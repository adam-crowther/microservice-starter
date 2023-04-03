package com.acroteq.food.ordering.system.order.service.application.mapper;

import com.acroteq.food.ordering.system.order.service.application.model.Order;
import com.acroteq.food.ordering.system.order.service.domain.dto.track.TrackOrderResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { OrderAddressDtoToApiMapper.class })
public interface TrackOrderResponseDtoToApiMapper {

  @Mapping(target = "address", source = "streetAddress")
  Order convertDtoToApi(TrackOrderResponseDto responseDto);
}
