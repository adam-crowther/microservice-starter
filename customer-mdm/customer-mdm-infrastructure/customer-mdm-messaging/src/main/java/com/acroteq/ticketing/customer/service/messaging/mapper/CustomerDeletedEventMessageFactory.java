package com.acroteq.ticketing.customer.service.messaging.mapper;

import com.acroteq.ticketing.application.mapper.id.CustomerIdMapper;
import com.acroteq.ticketing.customer.service.domain.event.CustomerDeletedEvent;
import com.acroteq.ticketing.infrastructure.mapper.EventToMessageMapper;
import com.acroteq.ticketing.kafka.customer.avro.model.CustomerDeletedEventMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = CustomerIdMapper.class)
public interface CustomerDeletedEventMessageFactory
    extends EventToMessageMapper<CustomerDeletedEvent, CustomerDeletedEventMessage> {

  @Mapping(target = "id", source = "customerId")
  @Override
  CustomerDeletedEventMessage convertEventToMessage(CustomerDeletedEvent event);
}
