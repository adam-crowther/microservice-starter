package com.acroteq.ticketing.payment.service.domain;

import com.acroteq.ticketing.payment.service.domain.dto.payment.PaymentCancelRequestDto;
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
  public void completePayment(final PaymentRequestDto dto) {
    final PaymentEvent paymentEvent = paymentProcessor.processPayment(dto);
    paymentEvent.accept(paymentEventPublisherVisitor);
  }

  @Override
  @Transactional
  public void cancelPayment(final PaymentCancelRequestDto dto) {
    final PaymentEvent paymentEvent = paymentProcessor.cancelPayment(dto);
    paymentEvent.accept(paymentEventPublisherVisitor);
  }
}
