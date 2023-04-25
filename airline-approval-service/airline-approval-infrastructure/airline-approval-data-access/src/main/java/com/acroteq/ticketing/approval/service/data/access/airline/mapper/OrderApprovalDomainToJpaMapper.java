package com.acroteq.ticketing.approval.service.data.access.airline.mapper;

import com.acroteq.ticketing.application.mapper.AirlineIdMapper;
import com.acroteq.ticketing.application.mapper.OrderIdMapper;
import com.acroteq.ticketing.approval.service.data.access.airline.entity.OrderApprovalJpaEntity;
import com.acroteq.ticketing.approval.service.domain.entity.order.OrderApproval;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { AirlineIdMapper.class, OrderIdMapper.class, OrderApprovalIdMapper.class })
public interface OrderApprovalDomainToJpaMapper {

  @Mapping(target = "airlineId", source = "airline.id")
  @Mapping(target = "status", source = "approvalStatus")
  OrderApprovalJpaEntity convertDomainToJpa(OrderApproval orderApproval);
}
