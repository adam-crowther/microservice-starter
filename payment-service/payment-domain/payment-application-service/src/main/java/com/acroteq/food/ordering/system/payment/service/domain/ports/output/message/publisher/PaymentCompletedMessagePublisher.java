package com.acroteq.food.ordering.system.payment.service.domain.ports.output.message.publisher;

import com.acroteq.food.ordering.system.domain.event.publisher.DomainEventPublisher;
import com.acroteq.food.ordering.system.payment.service.domain.event.PaymentCompletedEvent;

public interface PaymentCompletedMessagePublisher extends DomainEventPublisher<PaymentCompletedEvent> {
}
