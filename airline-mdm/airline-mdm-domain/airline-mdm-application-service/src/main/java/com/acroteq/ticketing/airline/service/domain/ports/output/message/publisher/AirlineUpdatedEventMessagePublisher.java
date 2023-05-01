package com.acroteq.ticketing.airline.service.domain.ports.output.message.publisher;

import com.acroteq.ticketing.airline.service.domain.event.AirlineUpdatedEvent;
import com.acroteq.ticketing.domain.event.publisher.DomainEventPublisher;

public interface AirlineUpdatedEventMessagePublisher extends DomainEventPublisher<AirlineUpdatedEvent> {}
