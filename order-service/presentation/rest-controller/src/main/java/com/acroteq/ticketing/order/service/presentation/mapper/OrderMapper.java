package com.acroteq.ticketing.order.service.presentation.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.OrderIdMapper;
import com.acroteq.domain.valueobject.OrderStatus;
import com.acroteq.ticketing.order.service.domain.entity.Order;
import com.acroteq.ticketing.order.service.domain.mapper.order.TrackingIdMapper;
import com.acroteq.ticketing.order.service.domain.resolver.AirlineResolver;
import com.acroteq.ticketing.order.service.domain.resolver.CustomerResolver;
import com.acroteq.ticketing.order.service.presentation.model.CreateOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfig.class,
        uses = { OrderIdMapper.class,
                 AuditMapper.class,
                 TrackingIdMapper.class,
                 AddressMapper.class,
                 OrderItemMapper.class,
                 OrderStatusMapper.class,
                 AirlineResolver.class,
                 CustomerResolver.class },
        imports = OrderStatus.class)
public interface OrderMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "version", ignore = true)
  @Mapping(target = "audit", ignore = true)
  @Mapping(target = "trackingId", ignore = true)
  @Mapping(target = "airline", source = "airlineId")
  @Mapping(target = "customer", source = "customerId")
  @Mapping(target = "orderStatus", ignore = true)
  @Mapping(target = "result", ignore = true)
  @Mapping(target = "streetAddress", source = "address")
  Order convert(CreateOrder createOrder);

  @Mapping(target = "airlineId", source = "airline.id.value")
  @Mapping(target = "customerId", source = "customer.id.value")
  @Mapping(target = "status", source = "orderStatus")
  @Mapping(target = "address", source = "streetAddress")
  com.acroteq.ticketing.order.service.presentation.model.Order convert(Order order);
}
