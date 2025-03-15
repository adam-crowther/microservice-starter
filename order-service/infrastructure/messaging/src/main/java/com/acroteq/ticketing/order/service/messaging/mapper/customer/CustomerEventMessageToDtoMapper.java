package com.acroteq.ticketing.order.service.messaging.mapper.customer;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.infrastructure.mapper.EventIdMessageToDtoMapper;
import com.acroteq.infrastructure.mapper.MessageToDtoMapper;
import com.acroteq.ticketing.kafka.customer.avro.model.CustomerEventMessage;
import com.acroteq.ticketing.order.service.domain.dto.customer.CustomerEventDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(config = MapstructConfig.class)
public abstract class CustomerEventMessageToDtoMapper
    implements MessageToDtoMapper<CustomerEventMessage, CustomerEventDto> {

  @Autowired
  EventIdMessageToDtoMapper eventIdMapper;

  @Mapping(target = "eventId", expression = "java(eventIdMapper.convertMessageToDto(partition, offset))")
  @Override
  public abstract CustomerEventDto convertMessageToDto(CustomerEventMessage message, Integer partition, Long offset);
}
