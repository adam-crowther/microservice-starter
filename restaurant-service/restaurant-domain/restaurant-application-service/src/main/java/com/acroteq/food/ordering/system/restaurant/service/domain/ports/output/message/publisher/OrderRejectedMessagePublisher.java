package com.acroteq.food.ordering.system.restaurant.service.domain.ports.output.message.publisher;

import com.acroteq.food.ordering.system.domain.event.publisher.DomainEventPublisher;
import com.acroteq.food.ordering.system.restaurant.service.domain.event.OrderRejectedEvent;

public interface OrderRejectedMessagePublisher extends DomainEventPublisher<OrderRejectedEvent> {
}
