package com.acroteq.ticketing.approval.service.domain.ports.output.message.publisher;

import com.acroteq.domain.event.publisher.DomainEventPublisher;
import com.acroteq.ticketing.approval.service.domain.event.order.OrderRejectedEvent;

public interface OrderRejectedMessagePublisher extends DomainEventPublisher<OrderRejectedEvent> {}
