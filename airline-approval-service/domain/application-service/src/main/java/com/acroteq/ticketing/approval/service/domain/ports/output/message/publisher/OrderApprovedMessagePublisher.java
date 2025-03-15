package com.acroteq.ticketing.approval.service.domain.ports.output.message.publisher;

import com.acroteq.domain.event.publisher.DomainEventPublisher;
import com.acroteq.ticketing.approval.service.domain.event.order.OrderApprovedEvent;

public interface OrderApprovedMessagePublisher extends DomainEventPublisher<OrderApprovedEvent> {}
