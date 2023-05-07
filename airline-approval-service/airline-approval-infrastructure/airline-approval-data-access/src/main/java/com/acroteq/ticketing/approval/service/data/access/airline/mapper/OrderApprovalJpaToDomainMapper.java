package com.acroteq.ticketing.approval.service.data.access.airline.mapper;

import com.acroteq.ticketing.application.mapper.id.AirlineIdMapper;
import com.acroteq.ticketing.application.mapper.id.OrderIdMapper;
import com.acroteq.ticketing.approval.service.data.access.airline.entity.OrderApprovalJpaEntity;
import com.acroteq.ticketing.approval.service.domain.entity.order.OrderApproval;
import com.acroteq.ticketing.infrastructure.mapper.JpaToDomainMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { AirlineIdMapper.class,
                 OrderIdMapper.class,
                 OrderApprovalIdMapper.class,
                 AirlineJpaToDomainMapper.class })
public interface OrderApprovalJpaToDomainMapper extends JpaToDomainMapper<OrderApprovalJpaEntity, OrderApproval> {

  @Mapping(target = "approvalStatus", source = "status")
  @Override
  OrderApproval convertJpaToDomain(OrderApprovalJpaEntity orderApprovalJpaEntity);
}
