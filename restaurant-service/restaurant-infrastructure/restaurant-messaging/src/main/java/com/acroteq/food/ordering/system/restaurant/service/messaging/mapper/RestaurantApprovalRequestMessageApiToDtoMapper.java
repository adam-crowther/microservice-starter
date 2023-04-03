package com.acroteq.food.ordering.system.restaurant.service.messaging.mapper;

import com.acroteq.food.ordering.system.common.application.mapper.IdMapper;
import com.acroteq.food.ordering.system.kafka.order.avro.model.RestaurantApprovalRequestMessage;
import com.acroteq.food.ordering.system.restaurant.service.domain.dto.RestaurantApprovalRequestDto;
import org.mapstruct.Mapper;

@Mapper(uses = { IdMapper.class, ProductApiToDtoMapper.class })
public interface RestaurantApprovalRequestMessageApiToDtoMapper {

  RestaurantApprovalRequestDto convertApiToDto(RestaurantApprovalRequestMessage requestMessage);
}
