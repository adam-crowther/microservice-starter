package com.acroteq.ticketing.order.service.messaging.mapper.customer;

import com.acroteq.ticketing.infrastructure.mapper.MessageToDtoMapper;
import com.acroteq.ticketing.kafka.customer.avro.model.CustomerCreatedEventMessage;
import com.acroteq.ticketing.order.service.domain.dto.customer.CustomerCreatedDto;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerCreatedEventMessageToDtoMapper
    extends MessageToDtoMapper<CustomerCreatedEventMessage, CustomerCreatedDto> {

  @Override
  CustomerCreatedDto convertMessageToDto(CustomerCreatedEventMessage message);
}
