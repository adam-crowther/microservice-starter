package com.acroteq.ticketing.payment.service.messaging.mapper.customer;

import com.acroteq.ticketing.application.mapper.id.CurrencyIdMapper;
import com.acroteq.ticketing.infrastructure.mapper.MessageToDtoMapper;
import com.acroteq.ticketing.kafka.customer.avro.model.CustomerUpdatedEventMessage;
import com.acroteq.ticketing.payment.service.domain.dto.customer.CustomerUpdatedDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = CurrencyIdMapper.class)
public interface CustomerUpdatedEventMessageToDtoMapper
    extends MessageToDtoMapper<CustomerUpdatedEventMessage, CustomerUpdatedDto> {

  @Mapping(target = "creditLimit.currencyId", source = "creditLimitCurrencyId")
  @Mapping(target = "creditLimit.amount", source = "creditLimitAmount")
  @Override
  CustomerUpdatedDto convertMessageToDto(CustomerUpdatedEventMessage message);
}
