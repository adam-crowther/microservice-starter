package com.acroteq.ticketing.airline.service.domain.ports.output.message.publisher;

import com.acroteq.domain.event.publisher.EntityEventPublisher;
import com.acroteq.ticketing.airline.service.domain.event.AirlineEvent;

public interface AirlineEventMessagePublisher extends EntityEventPublisher<AirlineEvent> {}
