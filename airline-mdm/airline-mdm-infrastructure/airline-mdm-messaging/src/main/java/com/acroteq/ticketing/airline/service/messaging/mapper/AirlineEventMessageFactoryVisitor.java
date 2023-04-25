package com.acroteq.ticketing.airline.service.messaging.mapper;

import com.acroteq.ticketing.airline.service.domain.event.AirlineCreateFailedEvent;
import com.acroteq.ticketing.airline.service.domain.event.AirlineCreatedEvent;
import com.acroteq.ticketing.airline.service.domain.event.AirlineDeletedEvent;
import com.acroteq.ticketing.airline.service.domain.event.AirlineUpdateFailedEvent;
import com.acroteq.ticketing.airline.service.domain.event.AirlineUpdatedEvent;
import com.acroteq.ticketing.airline.service.domain.event.visitor.AirlineEventVisitor;
import com.acroteq.ticketing.kafka.airline.avro.model.AirlineEventMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AirlineEventMessageFactoryVisitor implements AirlineEventVisitor<AirlineEventMessage> {

  private final AirlineEventMessageFactory airlineEventMessageFactory;

  @Override
  public AirlineEventMessage visit(final AirlineCreatedEvent event) {
    return airlineEventMessageFactory.createAirlineEventMessage(event);
  }

  @Override
  public AirlineEventMessage visit(final AirlineCreateFailedEvent event) {
    return airlineEventMessageFactory.createAirlineEventMessage(event);
  }

  @Override
  public AirlineEventMessage visit(final AirlineUpdatedEvent event) {
    return airlineEventMessageFactory.createAirlineEventMessage(event);
  }

  @Override
  public AirlineEventMessage visit(final AirlineUpdateFailedEvent event) {
    return airlineEventMessageFactory.createAirlineEventMessage(event);
  }

  @Override
  public AirlineEventMessage visit(final AirlineDeletedEvent event) {
    return airlineEventMessageFactory.createAirlineEventMessage(event);
  }
}
