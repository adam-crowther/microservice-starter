package com.acroteq.ticketing.order.service.messaging.mapper.customer;

import com.acroteq.ticketing.infrastructure.mapper.MessageToDtoMapper;
import com.acroteq.ticketing.kafka.customer.avro.model.CustomerDeletedEventMessage;
import com.acroteq.ticketing.order.service.domain.dto.customer.CustomerDeletedDto;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerDeletedEventMessageToDtoMapper
    extends MessageToDtoMapper<CustomerDeletedEventMessage, CustomerDeletedDto> {

  @Override
  CustomerDeletedDto convertMessageToDto(CustomerDeletedEventMessage message);
}
