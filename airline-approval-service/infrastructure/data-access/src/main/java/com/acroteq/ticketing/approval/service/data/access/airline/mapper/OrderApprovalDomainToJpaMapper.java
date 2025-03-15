package com.acroteq.ticketing.approval.service.data.access.airline.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.AirlineIdMapper;
import com.acroteq.application.mapper.id.OrderIdMapper;
import com.acroteq.infrastructure.mapper.DomainToJpaMapper;
import com.acroteq.ticketing.approval.service.data.access.airline.entity.OrderApprovalJpaEntity;
import com.acroteq.ticketing.approval.service.data.access.airline.resolver.AirlineJpaResolver;
import com.acroteq.ticketing.approval.service.domain.entity.order.OrderApproval;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapstructConfig.class,
        uses = { AirlineIdMapper.class, OrderIdMapper.class, OrderApprovalIdMapper.class, AirlineJpaResolver.class })
public interface OrderApprovalDomainToJpaMapper extends DomainToJpaMapper<OrderApproval, OrderApprovalJpaEntity> {

  @Mapping(target = "audit", ignore = true)
  @Mapping(target = "airline", source = "airline.id")
  @Mapping(target = "status", source = "approvalStatus")
  @Override
  OrderApprovalJpaEntity convertDomainToJpa(OrderApproval orderApproval);

  @Mapping(target = "audit", ignore = true)
  @Mapping(target = "airline", source = "airline.id")
  @Mapping(target = "status", source = "approvalStatus")
  @Override
  OrderApprovalJpaEntity convertDomainToJpa(OrderApproval entity, @MappingTarget OrderApprovalJpaEntity jpaEntity);
}
