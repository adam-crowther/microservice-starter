package com.acroteq.ticketing.customer.service.messaging.mapper;

import com.acroteq.ticketing.application.mapper.CurrencyIdMapper;
import com.acroteq.ticketing.application.mapper.CustomerIdMapper;
import com.acroteq.ticketing.application.mapper.DateTimeMapper;
import com.acroteq.ticketing.application.mapper.ValidationResultMapper;
import com.acroteq.ticketing.customer.service.domain.event.CustomerCreateFailedEvent;
import com.acroteq.ticketing.customer.service.domain.event.CustomerCreatedEvent;
import com.acroteq.ticketing.customer.service.domain.event.CustomerDeletedEvent;
import com.acroteq.ticketing.customer.service.domain.event.CustomerUpdateFailedEvent;
import com.acroteq.ticketing.customer.service.domain.event.CustomerUpdatedEvent;
import com.acroteq.ticketing.kafka.customer.avro.model.CustomerEventMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.util.UUID;

@Mapper(uses = { CustomerIdMapper.class, CurrencyIdMapper.class, ValidationResultMapper.class, DateTimeMapper.class },
        imports = { UUID.class, Instant.class })
public interface CustomerEventMessageFactory {

  @Mapping(target = "eventType", constant = "CREATED")
  @Mapping(target = "id", source = "customer.id")
  @Mapping(target = "userName", source = "customer.userName")
  @Mapping(target = "firstName", source = "customer.firstName")
  @Mapping(target = "lastName", source = "customer.lastName")
  @Mapping(target = "creditLimitCurrencyId", source = "customer.creditLimit.currencyId")
  @Mapping(target = "creditLimitAmount", source = "customer.creditLimit.amount")
  CustomerEventMessage createCustomerEventMessage(CustomerCreatedEvent event);

  @Mapping(target = "eventType", constant = "CREATE_FAILED")
  @Mapping(target = "id", source = "customer.id")
  @Mapping(target = "userName", source = "customer.userName")
  @Mapping(target = "firstName", source = "customer.firstName")
  @Mapping(target = "lastName", source = "customer.lastName")
  @Mapping(target = "creditLimitCurrencyId", source = "customer.creditLimit.currencyId")
  @Mapping(target = "creditLimitAmount", source = "customer.creditLimit.amount")
  CustomerEventMessage createCustomerEventMessage(CustomerCreateFailedEvent event);

  @Mapping(target = "eventType", constant = "UPDATED")
  @Mapping(target = "id", source = "customer.id")
  @Mapping(target = "userName", source = "customer.userName")
  @Mapping(target = "firstName", source = "customer.firstName")
  @Mapping(target = "lastName", source = "customer.lastName")
  @Mapping(target = "creditLimitCurrencyId", source = "customer.creditLimit.currencyId")
  @Mapping(target = "creditLimitAmount", source = "customer.creditLimit.amount")
  CustomerEventMessage createCustomerEventMessage(CustomerUpdatedEvent event);

  @Mapping(target = "eventType", constant = "UPDATE_FAILED")
  @Mapping(target = "id", source = "customer.id")
  @Mapping(target = "userName", source = "customer.userName")
  @Mapping(target = "firstName", source = "customer.firstName")
  @Mapping(target = "lastName", source = "customer.lastName")
  @Mapping(target = "creditLimitCurrencyId", source = "customer.creditLimit.currencyId")
  @Mapping(target = "creditLimitAmount", source = "customer.creditLimit.amount")
  CustomerEventMessage createCustomerEventMessage(CustomerUpdateFailedEvent event);

  @Mapping(target = "eventType", constant = "DELETED")
  @Mapping(target = "id", source = "customerId")
  @Mapping(target = "userName", ignore = true)
  @Mapping(target = "firstName", ignore = true)
  @Mapping(target = "lastName", ignore = true)
  @Mapping(target = "creditLimitCurrencyId", ignore = true)
  @Mapping(target = "creditLimitAmount", ignore = true)
  CustomerEventMessage createCustomerEventMessage(CustomerDeletedEvent event);
}
