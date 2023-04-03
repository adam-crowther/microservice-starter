package com.acroteq.food.ordering.system.customer.service.domain.event.visitor;

import com.acroteq.food.ordering.system.customer.service.domain.event.CustomerCreateFailedEvent;
import com.acroteq.food.ordering.system.customer.service.domain.event.CustomerCreatedEvent;
import com.acroteq.food.ordering.system.customer.service.domain.event.CustomerDeletedEvent;
import com.acroteq.food.ordering.system.customer.service.domain.event.CustomerUpdateFailedEvent;
import com.acroteq.food.ordering.system.customer.service.domain.event.CustomerUpdatedEvent;

public interface CustomerEventVisitor<T> {

  T visit(final CustomerCreatedEvent event);

  T visit(final CustomerCreateFailedEvent event);

  T visit(final CustomerUpdatedEvent event);

  T visit(final CustomerUpdateFailedEvent event);

  T visit(final CustomerDeletedEvent event);
}
