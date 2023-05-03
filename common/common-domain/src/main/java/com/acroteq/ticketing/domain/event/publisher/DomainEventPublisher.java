package com.acroteq.ticketing.domain.event.publisher;

import com.acroteq.ticketing.domain.event.Event;

public interface DomainEventPublisher<T extends Event> {

  void publish(T domainEvent);
}
