package com.acroteq.ticketing.customer.service.domain.ports.output.message.publisher;

import com.acroteq.ticketing.customer.service.domain.event.CustomerEvent;
import com.acroteq.ticketing.domain.event.publisher.EntityEventPublisher;

public interface CustomerEventMessagePublisher extends EntityEventPublisher<CustomerEvent> {}
