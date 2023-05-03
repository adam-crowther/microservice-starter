package com.acroteq.ticketing.approval.service.data.access.airline.mapper;

import com.acroteq.ticketing.application.mapper.id.AirlineIdMapper;
import com.acroteq.ticketing.application.mapper.id.OrderIdMapper;
import com.acroteq.ticketing.approval.service.data.access.airline.entity.OrderApprovalJpaEntity;
import com.acroteq.ticketing.approval.service.domain.entity.order.OrderApproval;
import com.acroteq.ticketing.infrastructure.mapper.DomainToJpaMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(uses = { AirlineIdMapper.class, OrderIdMapper.class, OrderApprovalIdMapper.class })
public interface OrderApprovalDomainToJpaMapper extends DomainToJpaMapper<OrderApproval, OrderApprovalJpaEntity> {

  @Mapping(target = "audit", ignore = true)
  @Mapping(target = "airlineId", source = "airline.id")
  @Mapping(target = "status", source = "approvalStatus")
  @Override
  OrderApprovalJpaEntity convertDomainToJpa(OrderApproval orderApproval);

  @Mapping(target = "audit", ignore = true)
  @Mapping(target = "airlineId", source = "airline.id")
  @Mapping(target = "status", source = "approvalStatus")
  @Override
  OrderApprovalJpaEntity convertDomainToJpa(OrderApproval entity, @MappingTarget OrderApprovalJpaEntity jpaEntity);
}
