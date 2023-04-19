package com.acroteq.ticketing.airline.service.data.access.airline.mapper;

import com.acroteq.ticketing.airline.service.data.access.airline.entity.OrderApprovalJpaEntity;
import com.acroteq.ticketing.airline.service.domain.entity.OrderApproval;
import com.acroteq.ticketing.application.mapper.AirlineIdMapper;
import com.acroteq.ticketing.application.mapper.OrderIdMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { AirlineIdMapper.class, OrderIdMapper.class, OrderApprovalIdMapper.class })
public interface OrderApprovalDomainToJpaMapper {

  @Mapping(target = "airlineId", source = "airline.id")
  @Mapping(target = "orderId", source = "order.id")
  @Mapping(target = "status", source = "approvalStatus")
  OrderApprovalJpaEntity convertDomainToEntity(OrderApproval orderApproval);
}
