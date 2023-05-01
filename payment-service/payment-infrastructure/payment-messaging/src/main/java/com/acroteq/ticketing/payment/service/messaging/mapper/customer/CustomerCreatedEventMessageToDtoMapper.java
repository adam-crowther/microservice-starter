package com.acroteq.ticketing.payment.service.messaging.mapper.customer;

import com.acroteq.ticketing.application.mapper.id.CurrencyIdMapper;
import com.acroteq.ticketing.infrastructure.mapper.MessageToDtoMapper;
import com.acroteq.ticketing.kafka.customer.avro.model.CustomerCreatedEventMessage;
import com.acroteq.ticketing.payment.service.domain.dto.customer.CustomerCreatedDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = CurrencyIdMapper.class)
public interface CustomerCreatedEventMessageToDtoMapper
    extends MessageToDtoMapper<CustomerCreatedEventMessage, CustomerCreatedDto> {

  @Mapping(target = "creditLimit.currencyId", source = "creditLimitCurrencyId")
  @Mapping(target = "creditLimit.amount", source = "creditLimitAmount")
  @Override
  CustomerCreatedDto convertMessageToDto(CustomerCreatedEventMessage message);
}
