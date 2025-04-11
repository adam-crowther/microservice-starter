package com.acroteq.ticketing.order.service.messaging.mapper.customer;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.MessageToDomainMapper;
import com.acroteq.application.mapper.id.CustomerIdMapper;
import com.acroteq.domain.valueobject.EventId;
import com.acroteq.ticketing.kafka.customer.avro.model.CustomerEventMessage;
import com.acroteq.ticketing.order.service.domain.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class, uses = { CustomerIdMapper.class })
public interface CustomerEventMapper extends MessageToDomainMapper<CustomerEventMessage, Customer> {

  @Override
  Customer convert(CustomerEventMessage message, EventId eventId);
}
