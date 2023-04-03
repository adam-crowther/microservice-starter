package com.acroteq.food.ordering.system.domain.event.publisher;

import com.acroteq.food.ordering.system.domain.event.DomainEvent;

public interface DomainEventPublisher<T extends DomainEvent<?>> {

  void publish(T domainEvent);
}
