package com.acroteq.ticketing.airline.service.domain.ports.output.message.publisher;

import com.acroteq.ticketing.airline.service.domain.event.AirlineDeletedEvent;
import com.acroteq.ticketing.domain.event.publisher.DomainEventPublisher;

public interface AirlineDeletedEventMessagePublisher extends DomainEventPublisher<AirlineDeletedEvent> {}
