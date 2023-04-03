package com.acroteq.food.ordering.system.order.service.messaging.mapper;

import com.acroteq.food.ordering.system.kafka.order.avro.model.OrderApprovalStatus;
import org.mapstruct.Mapper;

@Mapper
public interface OrderApprovalStatusMapper {

  com.acroteq.food.ordering.system.order.service.domain.dto.common.OrderApprovalStatus convert(final OrderApprovalStatus status);
}
