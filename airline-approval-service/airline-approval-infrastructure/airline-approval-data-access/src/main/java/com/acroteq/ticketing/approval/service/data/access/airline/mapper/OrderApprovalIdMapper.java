package com.acroteq.ticketing.approval.service.data.access.airline.mapper;

import com.acroteq.ticketing.application.mapper.id.IdMapper;
import com.acroteq.ticketing.approval.service.domain.valueobject.OrderApprovalId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface OrderApprovalIdMapper extends IdMapper<OrderApprovalId> {

  @Mapping(target = "value", source = "id")
  @Override
  OrderApprovalId convertLongToId(Long id);

  @Mapping(target = "value", source = "id")
  @Override
  OrderApprovalId convertStringToId(String id);
}
