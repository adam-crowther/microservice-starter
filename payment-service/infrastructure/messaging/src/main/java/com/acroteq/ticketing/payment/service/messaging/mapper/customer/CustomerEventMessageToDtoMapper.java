package com.acroteq.ticketing.payment.service.messaging.mapper.customer;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.CurrencyIdMapper;
import com.acroteq.infrastructure.mapper.EventIdMessageToDtoMapper;
import com.acroteq.infrastructure.mapper.MessageToDtoMapper;
import com.acroteq.ticketing.kafka.customer.avro.model.CustomerEventMessage;
import com.acroteq.ticketing.payment.service.domain.dto.customer.CustomerEventDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(config = MapstructConfig.class, uses = CurrencyIdMapper.class)
public abstract class CustomerEventMessageToDtoMapper
    implements MessageToDtoMapper<CustomerEventMessage, CustomerEventDto> {

  @Autowired
  EventIdMessageToDtoMapper eventIdMapper;

  @Mapping(target = "eventId", expression = "java(eventIdMapper.convertMessageToDto(partition, offset))")
  @Mapping(target = "creditLimit.currencyId", source = "message.creditLimitCurrencyId")
  @Mapping(target = "creditLimit.amount", source = "message.creditLimitAmount")
  @Override
  public abstract CustomerEventDto convertMessageToDto(CustomerEventMessage message, Integer partition, Long offset);
}
