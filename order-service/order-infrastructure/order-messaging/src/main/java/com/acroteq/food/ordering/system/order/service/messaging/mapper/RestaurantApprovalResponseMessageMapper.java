package com.acroteq.food.ordering.system.order.service.messaging.mapper;

import com.acroteq.food.ordering.system.common.application.mapper.IdMapper;
import com.acroteq.food.ordering.system.common.application.mapper.ValidationResultMapper;
import com.acroteq.food.ordering.system.kafka.order.avro.model.RestaurantApprovalResponseMessage;
import com.acroteq.food.ordering.system.order.service.domain.dto.message.RestaurantApprovalResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { IdMapper.class, OrderApprovalStatusMapper.class, ValidationResultMapper.class })
public interface RestaurantApprovalResponseMessageMapper {

  @Mapping(target = "result", source = "failureMessages")
  RestaurantApprovalResponseDto convertMessageToDto(RestaurantApprovalResponseMessage message);
}
