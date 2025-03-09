package com.acroteq.domain.event.publisher;

import com.acroteq.domain.event.Event;

public interface DomainEventPublisher<T extends Event> {

  void publish(T domainEvent);
}
