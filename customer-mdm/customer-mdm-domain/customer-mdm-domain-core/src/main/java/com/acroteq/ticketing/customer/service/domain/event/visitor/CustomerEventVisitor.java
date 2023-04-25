package com.acroteq.ticketing.customer.service.domain.event.visitor;

import com.acroteq.ticketing.customer.service.domain.event.CustomerCreateFailedEvent;
import com.acroteq.ticketing.customer.service.domain.event.CustomerCreatedEvent;
import com.acroteq.ticketing.customer.service.domain.event.CustomerDeletedEvent;
import com.acroteq.ticketing.customer.service.domain.event.CustomerUpdateFailedEvent;
import com.acroteq.ticketing.customer.service.domain.event.CustomerUpdatedEvent;

public interface CustomerEventVisitor<T> {

  T visit(CustomerCreatedEvent event);

  T visit(CustomerCreateFailedEvent event);

  T visit(CustomerUpdatedEvent event);

  T visit(CustomerUpdateFailedEvent event);

  T visit(CustomerDeletedEvent event);
}
