package com.acroteq.food.ordering.system.order.service.messaging.mapper;

import com.acroteq.food.ordering.system.kafka.order.avro.model.CustomerEventMessage;
import com.acroteq.food.ordering.system.order.service.domain.dto.customer.CustomerDto;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerEventMessageToDtoMapper {

  CustomerDto convertMessageToDto(CustomerEventMessage message);
}
