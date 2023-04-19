package com.acroteq.ticketing.order.service.domain.ports.output.message.publisher.payment;

import com.acroteq.ticketing.domain.event.publisher.DomainEventPublisher;
import com.acroteq.ticketing.order.service.domain.event.OrderCancelledEvent;

public interface OrderCancelledPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCancelledEvent> {

}
