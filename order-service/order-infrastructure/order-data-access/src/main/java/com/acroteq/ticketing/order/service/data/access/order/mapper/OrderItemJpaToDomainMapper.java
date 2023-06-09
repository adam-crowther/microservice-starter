package com.acroteq.ticketing.order.service.data.access.order.mapper;

import com.acroteq.ticketing.application.mapper.MapstructConfig;
import com.acroteq.ticketing.application.mapper.id.AirlineIdMapper;
import com.acroteq.ticketing.application.mapper.id.CustomerIdMapper;
import com.acroteq.ticketing.application.mapper.id.OrderIdMapper;
import com.acroteq.ticketing.domain.valueobject.FlightId;
import com.acroteq.ticketing.domain.valueobject.OrderId;
import com.acroteq.ticketing.domain.valueobject.OrderItemId;
import com.acroteq.ticketing.infrastructure.mapper.JpaToDomainMapper;
import com.acroteq.ticketing.order.service.data.access.airline.mapper.FlightJpaToDomainMapper;
import com.acroteq.ticketing.order.service.data.access.order.entity.OrderItemJpaEntity;
import com.acroteq.ticketing.order.service.domain.entity.OrderItem;
import com.acroteq.ticketing.order.service.domain.mapper.order.TrackingIdMapper;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class,
        uses = { OrderIdMapper.class,
                 OrderItemIdMapper.class,
                 TrackingIdMapper.class,
                 CustomerIdMapper.class,
                 AirlineIdMapper.class,
                 FlightJpaToDomainMapper.class },
        imports = { OrderItemId.class, OrderId.class, FlightId.class })
public interface OrderItemJpaToDomainMapper extends JpaToDomainMapper<OrderItemJpaEntity, OrderItem> {

  @Override
  OrderItem convertJpaToDomain(OrderItemJpaEntity orderItem);
}
