package com.acroteq.ticketing.approval.service.data.access.airline.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.AirlineIdMapper;
import com.acroteq.application.mapper.id.OrderIdMapper;
import com.acroteq.infrastructure.mapper.JpaToDomainMapper;
import com.acroteq.ticketing.approval.service.data.access.airline.entity.OrderApprovalJpaEntity;
import com.acroteq.ticketing.approval.service.domain.entity.order.OrderApproval;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfig.class,
        uses = { AirlineIdMapper.class,
                 OrderIdMapper.class,
                 OrderApprovalIdMapper.class,
                 AirlineJpaToDomainMapper.class })
public interface OrderApprovalJpaToDomainMapper extends JpaToDomainMapper<OrderApprovalJpaEntity, OrderApproval> {

  @Mapping(target = "approvalStatus", source = "status")
  @Override
  OrderApproval convertJpaToDomain(OrderApprovalJpaEntity orderApprovalJpaEntity);
}
