package com.acroteq.ticketing.order.service.messaging.mapper.customer;

import com.acroteq.ticketing.infrastructure.mapper.MessageToDtoMapper;
import com.acroteq.ticketing.kafka.customer.avro.model.CustomerUpdatedEventMessage;
import com.acroteq.ticketing.order.service.domain.dto.customer.CustomerUpdatedDto;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerUpdatedEventMessageToDtoMapper
    extends MessageToDtoMapper<CustomerUpdatedEventMessage, CustomerUpdatedDto> {

  @Override
  CustomerUpdatedDto convertMessageToDto(CustomerUpdatedEventMessage message);
}
