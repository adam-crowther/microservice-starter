package com.acroteq.ticketing.payment.service.messaging.mapper.customer;

import com.acroteq.ticketing.infrastructure.mapper.MessageToDtoMapper;
import com.acroteq.ticketing.kafka.customer.avro.model.CustomerDeletedEventMessage;
import com.acroteq.ticketing.payment.service.domain.dto.customer.CustomerDeletedDto;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerDeletedEventMessageToDtoMapper
    extends MessageToDtoMapper<CustomerDeletedEventMessage, CustomerDeletedDto> {

  @Override
  CustomerDeletedDto convertMessageToDto(CustomerDeletedEventMessage message);
}
