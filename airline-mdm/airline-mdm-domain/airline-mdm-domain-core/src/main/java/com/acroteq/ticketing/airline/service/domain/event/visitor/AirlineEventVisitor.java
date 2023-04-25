package com.acroteq.ticketing.airline.service.domain.event.visitor;

import com.acroteq.ticketing.airline.service.domain.event.AirlineCreateFailedEvent;
import com.acroteq.ticketing.airline.service.domain.event.AirlineCreatedEvent;
import com.acroteq.ticketing.airline.service.domain.event.AirlineDeletedEvent;
import com.acroteq.ticketing.airline.service.domain.event.AirlineUpdateFailedEvent;
import com.acroteq.ticketing.airline.service.domain.event.AirlineUpdatedEvent;

public interface AirlineEventVisitor<T> {

  T visit(AirlineCreatedEvent event);

  T visit(AirlineCreateFailedEvent event);

  T visit(AirlineUpdatedEvent event);

  T visit(AirlineUpdateFailedEvent event);

  T visit(AirlineDeletedEvent event);
}
