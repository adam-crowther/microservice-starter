package com.acroteq.domain.event.publisher;

import com.acroteq.domain.event.Event;

public interface EntityEventPublisher<EventT extends Event> {

  void publish(EventT domainEvent);

  void publishDelete(Long id);
}
