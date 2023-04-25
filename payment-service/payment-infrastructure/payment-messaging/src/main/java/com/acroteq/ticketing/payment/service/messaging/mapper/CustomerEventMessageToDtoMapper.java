package com.acroteq.ticketing.payment.service.messaging.mapper;

import com.acroteq.ticketing.application.mapper.CurrencyIdMapper;
import com.acroteq.ticketing.kafka.customer.avro.model.CustomerEventMessage;
import com.acroteq.ticketing.payment.service.domain.dto.customer.CustomerDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = CurrencyIdMapper.class)
public interface CustomerEventMessageToDtoMapper {

  @Mapping(target = "creditLimit.currencyId", source = "creditLimitCurrencyId")
  @Mapping(target = "creditLimit.amount", source = "creditLimitAmount")
  CustomerDto convertMessageToDto(CustomerEventMessage message);
}
