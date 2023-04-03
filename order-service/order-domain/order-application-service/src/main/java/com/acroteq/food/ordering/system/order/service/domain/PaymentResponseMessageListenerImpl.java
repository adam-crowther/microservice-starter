package com.acroteq.food.ordering.system.order.service.domain;

import com.acroteq.food.ordering.system.domain.validation.ValidationResult;
import com.acroteq.food.ordering.system.order.service.domain.dto.message.PaymentResponseDto;
import com.acroteq.food.ordering.system.order.service.domain.event.OrderPaidEvent;
import com.acroteq.food.ordering.system.order.service.domain.ports.input.message.listener.payment.PaymentResponseMessageListener;
import com.acroteq.food.ordering.system.order.service.domain.ports.output.message.publisher.restaurantapproval.OrderPaidRestaurantRequestMessagePublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Validated
@Service
public class PaymentResponseMessageListenerImpl implements PaymentResponseMessageListener {

  private final OrderPaymentSaga orderPaymentSaga;
  private final OrderPaidRestaurantRequestMessagePublisher orderPaidRestaurantRequestMessagePublisher;

  @Override
  public void paymentCompleted(final PaymentResponseDto paymentResponse) {
    final OrderPaidEvent orderPaidEvent = orderPaymentSaga.process(paymentResponse);
    final UUID orderId = paymentResponse.getOrderId();
    log.info("Publishing OrderPaidEvent for order id: {}", orderId);
    orderPaidRestaurantRequestMessagePublisher.publish(orderPaidEvent);
  }

  @Override
  public void paymentCancelled(final PaymentResponseDto paymentResponse) {
    orderPaymentSaga.rollback(paymentResponse);
    final UUID orderId = paymentResponse.getOrderId();
    final ValidationResult result = paymentResponse.getResult();
    log.info("Order is rolled back for order id {} with failure messages: {}", orderId, result.getLogOutput());
  }
}
