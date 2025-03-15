package com.acroteq.ticketing.customer.service.domain.ports.output.message.publisher;

import com.acroteq.domain.event.publisher.EntityEventPublisher;
import com.acroteq.ticketing.customer.service.domain.event.CustomerEvent;

public interface CustomerEventMessagePublisher extends EntityEventPublisher<CustomerEvent> {}
