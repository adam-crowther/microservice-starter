package com.acroteq.ticketing.approval.service.data.access.airline.mapper;

import com.acroteq.ticketing.application.mapper.id.AirlineIdMapper;
import com.acroteq.ticketing.application.mapper.id.OrderIdMapper;
import com.acroteq.ticketing.approval.service.data.access.airline.entity.OrderApprovalJpaEntity;
import com.acroteq.ticketing.approval.service.domain.entity.order.OrderApproval;
import com.acroteq.ticketing.infrastructure.mapper.DomainToJpaMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { AirlineIdMapper.class, OrderIdMapper.class, OrderApprovalIdMapper.class })
public interface OrderApprovalDomainToJpaMapper extends DomainToJpaMapper<OrderApproval, OrderApprovalJpaEntity> {

  @Mapping(target = "airlineId", source = "airline.id")
  @Mapping(target = "status", source = "approvalStatus")
  @Override
  OrderApprovalJpaEntity convertDomainToJpa(OrderApproval orderApproval);
}
