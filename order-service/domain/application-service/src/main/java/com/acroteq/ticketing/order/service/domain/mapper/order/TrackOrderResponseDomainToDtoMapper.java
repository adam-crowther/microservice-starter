package com.acroteq.ticketing.order.service.domain.mapper.order;

import com.acroteq.application.mapper.DomainToDtoMapper;
import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.AirlineIdMapper;
import com.acroteq.application.mapper.id.CustomerIdMapper;
import com.acroteq.application.mapper.id.OrderIdMapper;
import com.acroteq.ticketing.order.service.domain.dto.track.TrackOrderResponseDto;
import com.acroteq.ticketing.order.service.domain.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfig.class,
        uses = { TrackingIdMapper.class,
                 OrderIdMapper.class,
                 CustomerIdMapper.class,
                 AirlineIdMapper.class,
                 AddressDomainToDtoMapper.class,
                 OrderItemDomainToDtoMapper.class })
public interface TrackOrderResponseDomainToDtoMapper extends DomainToDtoMapper<Order, TrackOrderResponseDto> {

  @Mapping(target = "airlineId", source = "airline.id")
  @Mapping(target = "customerId", source = "customer.id")
  @Override
  TrackOrderResponseDto convertDomainToDto(Order order);
}
