package com.acroteq.ticketing.domain.event.publisher;

import com.acroteq.ticketing.domain.event.DomainEvent;

public interface DomainEventPublisher<T extends DomainEvent<?>> {

  void publish(T domainEvent);
}
