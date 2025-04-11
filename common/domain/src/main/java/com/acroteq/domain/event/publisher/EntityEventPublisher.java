package com.acroteq.domain.event.publisher;

import com.acroteq.domain.event.Event;

public interface EntityEventPublisher<KeyT, EventT extends Event> {

  void publish(EventT domainEvent);

  void publishDelete(KeyT key);
}
