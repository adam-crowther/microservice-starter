package com.acroteq.food.ordering.system.payment.service.domain;

import com.acroteq.food.ordering.system.payment.service.domain.dto.PaymentRequestDto;
import com.acroteq.food.ordering.system.payment.service.domain.event.PaymentEvent;
import com.acroteq.food.ordering.system.payment.service.domain.ports.input.message.listener.PaymentRequestMessageListener;
import com.acroteq.food.ordering.system.payment.service.domain.visitor.PaymentEventPublisherVisitor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentRequestMessageListenerImpl implements PaymentRequestMessageListener {

  private final PaymentProcessor paymentProcessor;
  private final PaymentEventPublisherVisitor paymentEventPublisherVisitor;

  @Override
  public void completePayment(final PaymentRequestDto paymentRequestDto) {
    final PaymentEvent paymentEvent = paymentProcessor.processPayment(paymentRequestDto);
    paymentEvent.accept(paymentEventPublisherVisitor);
  }

  @Override
  public void cancelPayment(final PaymentRequestDto paymentRequestDto) {
    final PaymentEvent paymentEvent = paymentProcessor.cancelPayment(paymentRequestDto);
    paymentEvent.accept(paymentEventPublisherVisitor);
  }
}
