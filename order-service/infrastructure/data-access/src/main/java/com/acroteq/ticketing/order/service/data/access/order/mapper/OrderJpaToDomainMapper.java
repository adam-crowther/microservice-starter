package com.acroteq.ticketing.order.service.data.access.order.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.ValidationResultMapper;
import com.acroteq.application.mapper.id.AirlineIdMapper;
import com.acroteq.application.mapper.id.CustomerIdMapper;
import com.acroteq.application.mapper.id.OrderIdMapper;
import com.acroteq.domain.validation.ValidationResult;
import com.acroteq.infrastructure.mapper.JpaToDomainMapper;
import com.acroteq.ticketing.order.service.data.access.airline.mapper.AirlineJpaToDomainMapper;
import com.acroteq.ticketing.order.service.data.access.customer.mapper.CustomerJpaToDomainMapper;
import com.acroteq.ticketing.order.service.data.access.order.entity.OrderJpaEntity;
import com.acroteq.ticketing.order.service.domain.entity.Order;
import com.acroteq.ticketing.order.service.domain.mapper.order.TrackingIdMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfig.class,
        uses = { OrderIdMapper.class,
                 TrackingIdMapper.class,
                 CustomerIdMapper.class,
                 AirlineIdMapper.class,
                 ValidationResultMapper.class,
                 OrderItemJpaToDomainMapper.class,
                 AddressJpaToDomainMapper.class,
                 CustomerJpaToDomainMapper.class,
                 AirlineJpaToDomainMapper.class },
        imports = ValidationResult.class)
public interface OrderJpaToDomainMapper extends JpaToDomainMapper<OrderJpaEntity, Order> {

  @Mapping(target = "streetAddress", source = "address")
  @Mapping(target = "result", source = "failureMessages")
  @Override
  Order convertJpaToDomain(OrderJpaEntity order);
}
