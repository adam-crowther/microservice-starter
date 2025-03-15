package com.acroteq.ticketing.payment.service.domain.ports.output.message.publisher;

import com.acroteq.domain.event.publisher.DomainEventPublisher;
import com.acroteq.ticketing.payment.service.domain.event.PaymentCancelledEvent;

public interface PaymentCancelledMessagePublisher extends DomainEventPublisher<PaymentCancelledEvent> {}
