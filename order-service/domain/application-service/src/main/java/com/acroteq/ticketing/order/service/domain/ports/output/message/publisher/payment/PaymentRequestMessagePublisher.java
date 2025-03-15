package com.acroteq.ticketing.order.service.domain.ports.output.message.publisher.payment;

import com.acroteq.domain.event.publisher.DomainEventPublisher;
import com.acroteq.ticketing.order.service.domain.event.OrderCreatedEvent;

public interface PaymentRequestMessagePublisher extends DomainEventPublisher<OrderCreatedEvent> {

}
