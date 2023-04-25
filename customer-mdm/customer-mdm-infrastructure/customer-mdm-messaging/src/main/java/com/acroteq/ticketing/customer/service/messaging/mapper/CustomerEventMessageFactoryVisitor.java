package com.acroteq.ticketing.customer.service.messaging.mapper;

import com.acroteq.ticketing.customer.service.domain.event.CustomerCreateFailedEvent;
import com.acroteq.ticketing.customer.service.domain.event.CustomerCreatedEvent;
import com.acroteq.ticketing.customer.service.domain.event.CustomerDeletedEvent;
import com.acroteq.ticketing.customer.service.domain.event.CustomerUpdateFailedEvent;
import com.acroteq.ticketing.customer.service.domain.event.CustomerUpdatedEvent;
import com.acroteq.ticketing.customer.service.domain.event.visitor.CustomerEventVisitor;
import com.acroteq.ticketing.kafka.customer.avro.model.CustomerEventMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerEventMessageFactoryVisitor implements CustomerEventVisitor<CustomerEventMessage> {

  private final CustomerEventMessageFactory customerEventMessageFactory;

  @Override
  public CustomerEventMessage visit(final CustomerCreatedEvent event) {
    return customerEventMessageFactory.createCustomerEventMessage(event);
  }

  @Override
  public CustomerEventMessage visit(final CustomerCreateFailedEvent event) {
    return customerEventMessageFactory.createCustomerEventMessage(event);
  }

  @Override
  public CustomerEventMessage visit(final CustomerUpdatedEvent event) {
    return customerEventMessageFactory.createCustomerEventMessage(event);
  }

  @Override
  public CustomerEventMessage visit(final CustomerUpdateFailedEvent event) {
    return customerEventMessageFactory.createCustomerEventMessage(event);
  }

  @Override
  public CustomerEventMessage visit(final CustomerDeletedEvent event) {
    return customerEventMessageFactory.createCustomerEventMessage(event);
  }
}