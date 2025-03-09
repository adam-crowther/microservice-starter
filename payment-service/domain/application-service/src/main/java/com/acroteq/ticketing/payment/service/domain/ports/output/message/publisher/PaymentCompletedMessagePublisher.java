package com.acroteq.ticketing.payment.service.domain.ports.output.message.publisher;

import com.acroteq.ticketing.domain.event.publisher.DomainEventPublisher;
import com.acroteq.ticketing.payment.service.domain.event.PaymentCompletedEvent;

public interface PaymentCompletedMessagePublisher extends DomainEventPublisher<PaymentCompletedEvent> {}
