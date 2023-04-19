package com.acroteq.ticketing.payment.service.domain.visitor;

import com.acroteq.ticketing.payment.service.domain.event.PaymentCancelledEvent;
import com.acroteq.ticketing.payment.service.domain.event.PaymentCompletedEvent;
import com.acroteq.ticketing.payment.service.domain.event.PaymentEvent;
import com.acroteq.ticketing.payment.service.domain.event.PaymentFailedEvent;
import com.acroteq.ticketing.payment.service.domain.event.visitor.PaymentEventVisitor;
import com.acroteq.ticketing.payment.service.domain.ports.output.message.publisher.PaymentCancelledMessagePublisher;
import com.acroteq.ticketing.payment.service.domain.ports.output.message.publisher.PaymentCompletedMessagePublisher;
import com.acroteq.ticketing.payment.service.domain.ports.output.message.publisher.PaymentFailedMessagePublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class PaymentEventPublisherVisitor implements PaymentEventVisitor {

  private final PaymentCompletedMessagePublisher paymentCompletedMessagePublisher;
  private final PaymentCancelledMessagePublisher paymentCancelledMessagePublisher;
  private final PaymentFailedMessagePublisher paymentFailedMessagePublisher;

  @Override
  public void visit(final PaymentCompletedEvent event) {
    logEvent("completed", event);
    paymentCompletedMessagePublisher.publish(event);
  }


  @Override
  public void visit(final PaymentCancelledEvent event) {
    logEvent("cancelled", event);
    paymentCancelledMessagePublisher.publish(event);
  }

  @Override
  public void visit(final PaymentFailedEvent event) {
    logEvent("failed", event);
    paymentFailedMessagePublisher.publish(event);
  }

  private void logEvent(final String eventType, final PaymentEvent event) {
    log.info("Publishing payment {} event with payment id: {} and order id: {}",
             eventType,
             event.getPayment()
                  .getId(),
             event.getPayment()
                  .getOrderId());
  }
}
