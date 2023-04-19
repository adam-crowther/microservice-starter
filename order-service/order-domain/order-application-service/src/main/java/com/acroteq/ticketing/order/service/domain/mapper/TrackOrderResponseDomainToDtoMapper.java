package com.acroteq.ticketing.order.service.domain.mapper;

import com.acroteq.ticketing.application.mapper.AirlineIdMapper;
import com.acroteq.ticketing.application.mapper.CustomerIdMapper;
import com.acroteq.ticketing.application.mapper.OrderIdMapper;
import com.acroteq.ticketing.order.service.domain.dto.track.TrackOrderResponseDto;
import com.acroteq.ticketing.order.service.domain.entity.Order;
import org.mapstruct.Mapper;

@Mapper(uses = { TrackingIdMapper.class,
                 OrderIdMapper.class,
                 CustomerIdMapper.class,
                 AirlineIdMapper.class,
                 AddressDomainToDtoMapper.class,
                 OrderItemDomainToDtoMapper.class })
public interface TrackOrderResponseDomainToDtoMapper {

  TrackOrderResponseDto convertDomainToDto(Order order);
}
