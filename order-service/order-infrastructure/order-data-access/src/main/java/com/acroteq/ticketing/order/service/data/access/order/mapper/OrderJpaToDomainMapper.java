package com.acroteq.ticketing.order.service.data.access.order.mapper;

import com.acroteq.ticketing.application.mapper.AirlineIdMapper;
import com.acroteq.ticketing.application.mapper.CustomerIdMapper;
import com.acroteq.ticketing.application.mapper.OrderIdMapper;
import com.acroteq.ticketing.application.mapper.ValidationResultMapper;
import com.acroteq.ticketing.domain.validation.ValidationResult;
import com.acroteq.ticketing.order.service.data.access.order.entity.OrderJpaEntity;
import com.acroteq.ticketing.order.service.domain.entity.Order;
import com.acroteq.ticketing.order.service.domain.mapper.TrackingIdMapper;
import com.acroteq.ticketing.order.service.domain.valueobject.TrackingId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(uses = { OrderIdMapper.class,
                 TrackingIdMapper.class,
                 CustomerIdMapper.class,
                 AirlineIdMapper.class,
                 ValidationResultMapper.class,
                 OrderItemJpaToDomainMapper.class,
                 AddressJpaToDomainMapper.class }, imports = ValidationResult.class)
public interface OrderJpaToDomainMapper {

  @Mapping(target = "streetAddress", source = "address")
  @Mapping(target = "result", source = "failureMessages")
  Order convertJpaToDomain(OrderJpaEntity order);

  @Mapping(target = "value", source = "uuid")
  TrackingId convertUuidToTrackingId(UUID uuid);
}
