package com.acroteq.ticketing.customer.service.messaging.mapper;

import com.acroteq.application.mapper.DateTimeMapper;
import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.ValidationResultMapper;
import com.acroteq.application.mapper.id.CurrencyIdMapper;
import com.acroteq.application.mapper.id.CustomerIdMapper;
import com.acroteq.infrastructure.mapper.EventToMessageMapper;
import com.acroteq.ticketing.customer.service.domain.event.CustomerEvent;
import com.acroteq.ticketing.kafka.customer.avro.model.CustomerEventMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.util.UUID;

@Mapper(config = MapstructConfig.class,
        uses = { CustomerIdMapper.class, CurrencyIdMapper.class, ValidationResultMapper.class, DateTimeMapper.class },
        imports = { UUID.class, Instant.class })
public interface CustomerEventMessageFactory extends EventToMessageMapper<CustomerEvent, CustomerEventMessage> {

  @Mapping(target = "id", source = "customer.id")
  @Mapping(target = "version", source = "customer.version")
  @Mapping(target = "audit", source = "customer.audit")
  @Mapping(target = "userName", source = "customer.userName")
  @Mapping(target = "firstName", source = "customer.firstName")
  @Mapping(target = "lastName", source = "customer.lastName")
  @Mapping(target = "creditLimitCurrencyId", source = "customer.creditLimit.currencyId")
  @Mapping(target = "creditLimitAmount", source = "customer.creditLimit.amount")
  @Mapping(target = "auditBuilder", ignore = true)
  @Override
  CustomerEventMessage convert(CustomerEvent event);
}
