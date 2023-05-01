package com.acroteq.ticketing.customer.service.domain.ports.output.message.publisher;

import com.acroteq.ticketing.customer.service.domain.event.CustomerDeletedEvent;
import com.acroteq.ticketing.domain.event.publisher.DomainEventPublisher;

public interface CustomerDeletedEventMessagePublisher extends DomainEventPublisher<CustomerDeletedEvent> {}
