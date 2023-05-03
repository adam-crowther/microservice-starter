package com.acroteq.ticketing.domain.event.publisher;

import com.acroteq.ticketing.domain.event.Event;

public interface EntityEventPublisher<EventT extends Event> {

  void publish(EventT domainEvent);

  void publishDelete(Long id);
}
