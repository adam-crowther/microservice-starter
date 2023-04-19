package com.acroteq.ticketing.airline.service.data.access.airline.mapper;

import com.acroteq.ticketing.airline.service.data.access.airline.entity.OrderApprovalJpaEntity;
import com.acroteq.ticketing.airline.service.domain.entity.OrderApproval;
import com.acroteq.ticketing.airline.service.domain.resolver.AirlineResolver;
import com.acroteq.ticketing.airline.service.domain.resolver.OrderResolver;
import com.acroteq.ticketing.application.mapper.AirlineIdMapper;
import com.acroteq.ticketing.application.mapper.OrderIdMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { AirlineIdMapper.class,
                 OrderIdMapper.class,
                 OrderApprovalIdMapper.class,
                 AirlineResolver.class,
                 OrderResolver.class })
public interface OrderApprovalJpaToDomainMapper {

  @Mapping(target = "approvalStatus", source = "status")
  @Mapping(target = "airline", source = "airlineId")
  @Mapping(target = "order", source = "orderId")
  OrderApproval convertEntityToDomain(OrderApprovalJpaEntity orderApprovalJpaEntity);
}
