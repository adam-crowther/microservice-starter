package com.acroteq.ticketing.order.service.domain;

import com.acroteq.ticketing.domain.validation.ValidationResult;
import com.acroteq.ticketing.order.service.domain.dto.message.PaymentCancelledResponseDto;
import com.acroteq.ticketing.order.service.domain.dto.message.PaymentPaidResponseDto;
import com.acroteq.ticketing.order.service.domain.event.OrderPaidEvent;
import com.acroteq.ticketing.order.service.domain.ports.input.message.listener.payment.PaymentResponseMessageListener;
import com.acroteq.ticketing.order.service.domain.ports.output.message.publisher.airlineapproval.AirlineApprovalRequestMessagePublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Slf4j
@RequiredArgsConstructor
@Validated
@Service
public class PaymentResponseMessageListenerImpl implements PaymentResponseMessageListener {

  private final OrderPaymentSagaStep saga;
  private final AirlineApprovalRequestMessagePublisher messagePublisher;

  @Override
  @Transactional
  public void paymentCompleted(final PaymentPaidResponseDto paymentResponse) {
    final OrderPaidEvent orderPaidEvent = saga.process(paymentResponse);
    final Long orderId = paymentResponse.getOrderId();
    log.info("Publishing OrderPaidEvent for order id: {}", orderId);
    messagePublisher.publish(orderPaidEvent);
  }

  @Override
  @Transactional
  public void paymentCancelled(final PaymentCancelledResponseDto paymentResponse) {
    saga.rollback(paymentResponse);
    final Long orderId = paymentResponse.getOrderId();
    final ValidationResult result = paymentResponse.getResult();
    log.info("Order is rolled back for order id {} with failure messages: {}", orderId, result.getLogOutput());
  }
}
