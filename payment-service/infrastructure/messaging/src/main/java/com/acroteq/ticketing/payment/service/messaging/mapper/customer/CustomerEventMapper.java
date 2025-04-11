package com.acroteq.ticketing.payment.service.messaging.mapper.customer;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.MessageToDomainMapper;
import com.acroteq.application.mapper.id.CurrencyIdMapper;
import com.acroteq.application.mapper.id.CustomerIdMapper;
import com.acroteq.domain.valueobject.EventId;
import com.acroteq.ticketing.kafka.customer.avro.model.CustomerEventMessage;
import com.acroteq.ticketing.payment.service.domain.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfig.class, uses = { CustomerIdMapper.class, CurrencyIdMapper.class })
public abstract class CustomerEventMapper implements MessageToDomainMapper<CustomerEventMessage, Customer> {

  @Mapping(target = "creditLimit.currencyId", source = "message.creditLimitCurrencyId")
  @Mapping(target = "creditLimit.amount", source = "message.creditLimitAmount")
  @Override
  public abstract Customer convert(CustomerEventMessage message, EventId eventId);
}
