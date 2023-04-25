package com.acroteq.ticketing.order.service.messaging.mapper;

import com.acroteq.ticketing.kafka.customer.avro.model.CustomerEventMessage;
import com.acroteq.ticketing.order.service.domain.dto.customer.CustomerDto;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerEventMessageToDtoMapper {

  CustomerDto convertMessageToDto(CustomerEventMessage message);
}
