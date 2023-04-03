package com.acroteq.food.ordering.system.restaurant.service.data.access.restaurant.mapper;

import com.acroteq.food.ordering.system.restaurant.service.domain.valueobject.OrderApprovalId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper
public interface OrderApprovalIdMapper {

  @Mapping(target = "value", source = "id")
  OrderApprovalId convertUuidToOrderApprovalId(final UUID id);

  default UUID convertOrderApprovalIdToUuid(final OrderApprovalId orderApprovalId) {
    return orderApprovalId.getValue();
  }
}
