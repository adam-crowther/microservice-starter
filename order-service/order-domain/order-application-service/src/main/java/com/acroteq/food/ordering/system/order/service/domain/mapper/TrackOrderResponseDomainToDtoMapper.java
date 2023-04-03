package com.acroteq.food.ordering.system.order.service.domain.mapper;

import com.acroteq.food.ordering.system.common.application.mapper.IdMapper;
import com.acroteq.food.ordering.system.order.service.domain.dto.track.TrackOrderResponseDto;
import com.acroteq.food.ordering.system.order.service.domain.entity.Order;
import org.mapstruct.Mapper;

@Mapper(uses = { IdMapper.class, AddressDomainToDtoMapper.class })
public interface TrackOrderResponseDomainToDtoMapper {

  TrackOrderResponseDto convertDomainToDto(Order order);
}
