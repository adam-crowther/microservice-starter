package com.acroteq.ticketing.airline.service.domain.ports.output.message.publisher;

import com.acroteq.ticketing.airline.service.domain.event.AirlineEvent;
import com.acroteq.ticketing.domain.event.publisher.EntityEventPublisher;

public interface AirlineEventMessagePublisher extends EntityEventPublisher<AirlineEvent> {}
