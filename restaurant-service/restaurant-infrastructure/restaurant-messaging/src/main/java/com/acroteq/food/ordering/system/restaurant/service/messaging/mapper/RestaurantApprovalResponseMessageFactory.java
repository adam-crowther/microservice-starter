package com.acroteq.food.ordering.system.restaurant.service.messaging.mapper;


import com.acroteq.food.ordering.system.common.application.mapper.DateTimeMapper;
import com.acroteq.food.ordering.system.common.application.mapper.IdMapper;
import com.acroteq.food.ordering.system.common.application.mapper.ValidationResultMapper;
import com.acroteq.food.ordering.system.kafka.order.avro.model.RestaurantApprovalResponseMessage;
import com.acroteq.food.ordering.system.restaurant.service.domain.event.OrderApprovedEvent;
import com.acroteq.food.ordering.system.restaurant.service.domain.event.OrderRejectedEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(uses = { IdMapper.class, DateTimeMapper.class, ValidationResultMapper.class },
        imports = UUID.class)
public interface RestaurantApprovalResponseMessageFactory {

  @Mapping(target = "id", expression = "java(UUID.randomUUID().toString())")
  @Mapping(target = "sagaId", constant = "")
  @Mapping(target = "orderId", source = "orderApproval.orderId")
  @Mapping(target = "restaurantId", source = "restaurantId")
  @Mapping(target = "orderApprovalStatus", source = "orderApproval.approvalStatus")
  @Mapping(target = "failureMessages", source = "result")
  RestaurantApprovalResponseMessage createRestaurantApprovalResponseMessage(OrderApprovedEvent event);

  @Mapping(target = "id", expression = "java(UUID.randomUUID().toString())")
  @Mapping(target = "sagaId", constant = "")
  @Mapping(target = "orderId", source = "orderApproval.orderId")
  @Mapping(target = "restaurantId", source = "restaurantId")
  @Mapping(target = "orderApprovalStatus", source = "orderApproval.approvalStatus")
  @Mapping(target = "failureMessages", source = "result")
  RestaurantApprovalResponseMessage createRestaurantApprovalResponseMessage(OrderRejectedEvent event);
}
