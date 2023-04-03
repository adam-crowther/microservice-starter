package com.acroteq.food.ordering.system.payment.service.domain.ports.output.message.publisher;

import com.acroteq.food.ordering.system.domain.event.publisher.DomainEventPublisher;
import com.acroteq.food.ordering.system.payment.service.domain.event.PaymentCancelledEvent;

public interface PaymentCancelledMessagePublisher extends DomainEventPublisher<PaymentCancelledEvent> {
}
