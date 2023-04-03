package com.acroteq.food.ordering.system.customer.service.messaging.mapper;

import com.acroteq.food.ordering.system.common.application.mapper.DateTimeMapper;
import com.acroteq.food.ordering.system.common.application.mapper.IdMapper;
import com.acroteq.food.ordering.system.common.application.mapper.ValidationResultMapper;
import com.acroteq.food.ordering.system.customer.service.domain.event.CustomerCreateFailedEvent;
import com.acroteq.food.ordering.system.customer.service.domain.event.CustomerCreatedEvent;
import com.acroteq.food.ordering.system.customer.service.domain.event.CustomerDeletedEvent;
import com.acroteq.food.ordering.system.customer.service.domain.event.CustomerUpdateFailedEvent;
import com.acroteq.food.ordering.system.customer.service.domain.event.CustomerUpdatedEvent;
import com.acroteq.food.ordering.system.kafka.order.avro.model.CustomerEventMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.util.UUID;

@Mapper(uses = { IdMapper.class, ValidationResultMapper.class, DateTimeMapper.class },
        imports = { UUID.class, Instant.class })
public interface CustomerEventMessageFactory {

  @Mapping(target = "eventType", constant = "CREATED")
  @Mapping(target = "id", source = "customer.id")
  @Mapping(target = "userName", source = "customer.userName")
  @Mapping(target = "firstName", source = "customer.firstName")
  @Mapping(target = "lastName", source = "customer.lastName")
  CustomerEventMessage createCustomerEventMessage(CustomerCreatedEvent event);

  @Mapping(target = "eventType", constant = "CREATE_FAILED")
  @Mapping(target = "id", source = "customer.id")
  @Mapping(target = "userName", source = "customer.userName")
  @Mapping(target = "firstName", source = "customer.firstName")
  @Mapping(target = "lastName", source = "customer.lastName")
  CustomerEventMessage createCustomerEventMessage(CustomerCreateFailedEvent event);

  @Mapping(target = "eventType", constant = "UPDATED")
  @Mapping(target = "id", source = "customer.id")
  @Mapping(target = "userName", source = "customer.userName")
  @Mapping(target = "firstName", source = "customer.firstName")
  @Mapping(target = "lastName", source = "customer.lastName")
  CustomerEventMessage createCustomerEventMessage(CustomerUpdatedEvent event);

  @Mapping(target = "eventType", constant = "UPDATE_FAILED")
  @Mapping(target = "id", source = "customer.id")
  @Mapping(target = "userName", source = "customer.userName")
  @Mapping(target = "firstName", source = "customer.firstName")
  @Mapping(target = "lastName", source = "customer.lastName")
  CustomerEventMessage createCustomerEventMessage(CustomerUpdateFailedEvent event);

  @Mapping(target = "eventType", constant = "DELETED")
  @Mapping(target = "id", source = "customerId")
  @Mapping(target = "userName", ignore = true)
  @Mapping(target = "firstName", ignore = true)
  @Mapping(target = "lastName", ignore = true)
  CustomerEventMessage createCustomerEventMessage(CustomerDeletedEvent event);
}
