package com.acroteq.ticketing.approval.service.data.access.airline.mapper;

import com.acroteq.ticketing.application.mapper.AirlineIdMapper;
import com.acroteq.ticketing.application.mapper.OrderIdMapper;
import com.acroteq.ticketing.approval.service.data.access.airline.entity.OrderApprovalJpaEntity;
import com.acroteq.ticketing.approval.service.domain.entity.order.OrderApproval;
import com.acroteq.ticketing.approval.service.domain.resolver.AirlineResolver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { AirlineIdMapper.class, OrderIdMapper.class, OrderApprovalIdMapper.class, AirlineResolver.class })
public interface OrderApprovalJpaToDomainMapper {

  @Mapping(target = "approvalStatus", source = "status")
  @Mapping(target = "airline", source = "airlineId")
  OrderApproval convertJpaToDomain(OrderApprovalJpaEntity orderApprovalJpaEntity);
}
