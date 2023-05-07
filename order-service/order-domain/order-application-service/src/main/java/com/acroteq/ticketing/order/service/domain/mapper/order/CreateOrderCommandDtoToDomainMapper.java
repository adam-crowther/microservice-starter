package com.acroteq.ticketing.order.service.domain.mapper.order;

import com.acroteq.ticketing.application.mapper.DtoToDomainMapper;
import com.acroteq.ticketing.application.mapper.id.AirlineIdMapper;
import com.acroteq.ticketing.application.mapper.id.CustomerIdMapper;
import com.acroteq.ticketing.application.mapper.id.OrderIdMapper;
import com.acroteq.ticketing.order.service.domain.dto.create.CreateOrderCommandDto;
import com.acroteq.ticketing.order.service.domain.entity.Order;
import com.acroteq.ticketing.order.service.domain.resolver.AirlineResolver;
import com.acroteq.ticketing.order.service.domain.resolver.CustomerResolver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { OrderItemDtoToDomainMapper.class,
                 OrderIdMapper.class,
                 CustomerIdMapper.class,
                 AirlineIdMapper.class,
                 AddressDtoToDomainMapper.class,
                 AirlineResolver.class,
                 CustomerResolver.class })
public interface CreateOrderCommandDtoToDomainMapper extends DtoToDomainMapper<CreateOrderCommandDto, Order> {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "airline", source = "airlineId")
  @Mapping(target = "customer", source = "customerId")
  @Mapping(target = "version", ignore = true)
  @Mapping(target = "audit", ignore = true)
  @Mapping(target = "streetAddress", source = "address")
  @Mapping(target = "trackingId", ignore = true)
  @Mapping(target = "orderStatus", ignore = true)
  @Mapping(target = "result", ignore = true)
  @Override
  Order convertDtoToDomain(CreateOrderCommandDto createOrderCommand);
}
