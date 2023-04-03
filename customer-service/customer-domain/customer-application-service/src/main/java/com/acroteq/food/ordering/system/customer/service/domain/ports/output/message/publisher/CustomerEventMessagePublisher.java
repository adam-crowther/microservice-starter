package com.acroteq.food.ordering.system.customer.service.domain.ports.output.message.publisher;

import com.acroteq.food.ordering.system.customer.service.domain.event.CustomerEvent;
import com.acroteq.food.ordering.system.domain.event.publisher.DomainEventPublisher;

public interface CustomerEventMessagePublisher extends DomainEventPublisher<CustomerEvent> {
}
