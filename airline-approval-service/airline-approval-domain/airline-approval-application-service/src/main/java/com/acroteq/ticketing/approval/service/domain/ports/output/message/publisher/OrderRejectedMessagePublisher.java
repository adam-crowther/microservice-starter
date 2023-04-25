package com.acroteq.ticketing.approval.service.domain.ports.output.message.publisher;

import com.acroteq.ticketing.approval.service.domain.event.order.OrderRejectedEvent;
import com.acroteq.ticketing.domain.event.publisher.DomainEventPublisher;

public interface OrderRejectedMessagePublisher extends DomainEventPublisher<OrderRejectedEvent> {}
