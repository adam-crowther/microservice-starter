package com.acroteq.ticketing.order.service.domain.mapper.order;

import com.acroteq.ticketing.application.mapper.DtoToDomainMapper;
import com.acroteq.ticketing.application.mapper.id.AirlineIdMapper;
import com.acroteq.ticketing.application.mapper.id.CustomerIdMapper;
import com.acroteq.ticketing.application.mapper.id.OrderIdMapper;
import com.acroteq.ticketing.order.service.domain.dto.create.CreateOrderCommandDto;
import com.acroteq.ticketing.order.service.domain.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { OrderItemDtoToDomainMapper.class,
                 OrderIdMapper.class,
                 CustomerIdMapper.class,
                 AirlineIdMapper.class,
                 AddressDtoToDomainMapper.class })
public interface CreateOrderCommandDtoToDomainMapper extends DtoToDomainMapper<CreateOrderCommandDto, Order> {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "streetAddress", source = "address")
  @Mapping(target = "trackingId", ignore = true)
  @Mapping(target = "orderStatus", ignore = true)
  @Mapping(target = "result", ignore = true)
  @Override
  Order convertDtoToDomain(CreateOrderCommandDto createOrderCommand);
}
