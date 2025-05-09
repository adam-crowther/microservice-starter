package com.acroteq.ticketing.approval.service.data.access.airline.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.AirlineIdMapper;
import com.acroteq.application.mapper.id.OrderIdMapper;
import com.acroteq.infrastructure.mapper.JpaMapper;
import com.acroteq.mapstruct.Resolve;
import com.acroteq.ticketing.approval.service.data.access.airline.entity.OrderApprovalJpaEntity;
import com.acroteq.ticketing.approval.service.data.access.airline.repository.OrderApprovalJpaRepository;
import com.acroteq.ticketing.approval.service.domain.entity.order.OrderApproval;
import lombok.Getter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(config = MapstructConfig.class,
        uses = { AirlineIdMapper.class, OrderIdMapper.class, OrderApprovalIdMapper.class, AirlineJpaMapper.class })
public abstract class OrderApprovalJpaMapper implements JpaMapper<OrderApproval, OrderApprovalJpaEntity> {

  @Getter
  @Autowired
  private OrderApprovalJpaRepository repository;

  @Mapping(target = "approvalStatus", source = "status")
  @Override
  public abstract OrderApproval convert(OrderApprovalJpaEntity orderApprovalJpaEntity);

  @Mapping(target = "audit", ignore = true)
  @Mapping(target = "airline", qualifiedBy = Resolve.class)
  @Mapping(target = "status", source = "approvalStatus")
  @Override
  public abstract OrderApprovalJpaEntity convertNew(OrderApproval orderApproval);

  @Mapping(target = "audit", ignore = true)
  @Mapping(target = "airline", qualifiedBy = Resolve.class)
  @Mapping(target = "status", source = "approvalStatus")
  @Override
  public abstract OrderApprovalJpaEntity convertExisting(
      OrderApproval entity,
      @MappingTarget OrderApprovalJpaEntity jpaEntity);
}
