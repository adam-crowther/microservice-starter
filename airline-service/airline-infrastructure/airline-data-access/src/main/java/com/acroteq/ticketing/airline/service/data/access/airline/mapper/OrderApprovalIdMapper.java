package com.acroteq.ticketing.airline.service.data.access.airline.mapper;

import com.acroteq.ticketing.airline.service.domain.valueobject.OrderApprovalId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface OrderApprovalIdMapper {

  @Mapping(target = "value", source = "id")
  OrderApprovalId convertLongToOrderApprovalId(Long id);

  default Long convertOrderApprovalIdToLong(final OrderApprovalId orderApprovalId) {
    return orderApprovalId.getValue();
  }
}
