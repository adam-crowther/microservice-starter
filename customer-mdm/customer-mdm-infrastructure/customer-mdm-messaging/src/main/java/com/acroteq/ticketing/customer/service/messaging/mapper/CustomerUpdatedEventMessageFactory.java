package com.acroteq.ticketing.customer.service.messaging.mapper;

import com.acroteq.ticketing.application.mapper.id.CurrencyIdMapper;
import com.acroteq.ticketing.application.mapper.id.CustomerIdMapper;
import com.acroteq.ticketing.application.mapper.DateTimeMapper;
import com.acroteq.ticketing.application.mapper.ValidationResultMapper;
import com.acroteq.ticketing.customer.service.domain.event.CustomerUpdatedEvent;
import com.acroteq.ticketing.infrastructure.mapper.EventToMessageMapper;
import com.acroteq.ticketing.kafka.customer.avro.model.CustomerUpdatedEventMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.util.UUID;

@Mapper(uses = { CustomerIdMapper.class, CurrencyIdMapper.class, ValidationResultMapper.class, DateTimeMapper.class },
        imports = { UUID.class, Instant.class })
public interface CustomerUpdatedEventMessageFactory
    extends EventToMessageMapper<CustomerUpdatedEvent, CustomerUpdatedEventMessage> {

  @Mapping(target = "id", source = "customer.id")
  @Mapping(target = "userName", source = "customer.userName")
  @Mapping(target = "firstName", source = "customer.firstName")
  @Mapping(target = "lastName", source = "customer.lastName")
  @Mapping(target = "creditLimitCurrencyId", source = "customer.creditLimit.currencyId")
  @Mapping(target = "creditLimitAmount", source = "customer.creditLimit.amount")
  @Override
  CustomerUpdatedEventMessage convertEventToMessage(CustomerUpdatedEvent event);
}
