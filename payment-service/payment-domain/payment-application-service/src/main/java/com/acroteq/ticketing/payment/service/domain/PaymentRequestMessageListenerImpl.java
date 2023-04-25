package com.acroteq.ticketing.payment.service.domain;

import com.acroteq.ticketing.payment.service.domain.dto.payment.PaymentRequestDto;
import com.acroteq.ticketing.payment.service.domain.event.PaymentEvent;
import com.acroteq.ticketing.payment.service.domain.ports.input.message.listener.PaymentRequestMessageListener;
import com.acroteq.ticketing.payment.service.domain.visitor.PaymentEventPublisherVisitor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentRequestMessageListenerImpl implements PaymentRequestMessageListener {

  private final PaymentProcessor paymentProcessor;
  private final PaymentEventPublisherVisitor paymentEventPublisherVisitor;

  @Override
  @Transactional
  public void completePayment(final PaymentRequestDto paymentRequestDto) {
    final PaymentEvent paymentEvent = paymentProcessor.processPayment(paymentRequestDto);
    paymentEvent.accept(paymentEventPublisherVisitor);
  }

  @Override
  @Transactional
  public void cancelPayment(final PaymentRequestDto paymentRequestDto) {
    final PaymentEvent paymentEvent = paymentProcessor.cancelPayment(paymentRequestDto);
    paymentEvent.accept(paymentEventPublisherVisitor);
  }
}
