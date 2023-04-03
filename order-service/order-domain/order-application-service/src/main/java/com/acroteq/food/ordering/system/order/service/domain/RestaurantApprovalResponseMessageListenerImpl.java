package com.acroteq.food.ordering.system.order.service.domain;

import com.acroteq.food.ordering.system.domain.validation.ValidationResult;
import com.acroteq.food.ordering.system.order.service.domain.dto.message.RestaurantApprovalResponseDto;
import com.acroteq.food.ordering.system.order.service.domain.event.OrderCancelledEvent;
import com.acroteq.food.ordering.system.order.service.domain.ports.input.message.listener.restaurantapproval.RestaurantApprovalResponseMessageListener;
import com.acroteq.food.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCancelledPaymentRequestMessagePublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Validated
@Service
public class RestaurantApprovalResponseMessageListenerImpl implements RestaurantApprovalResponseMessageListener {

  private final OrderApprovalSaga orderApprovalSaga;
  private final OrderCancelledPaymentRequestMessagePublisher orderCancelledPaymentRequestMessagePublisher;

  @Override
  public void orderApproved(final RestaurantApprovalResponseDto approvalResponse) {
    orderApprovalSaga.process(approvalResponse);
    final UUID orderId = approvalResponse.getOrderId();
    log.info("Order with order id {} is approved", orderId);
  }

  @Override
  public void orderRejected(final RestaurantApprovalResponseDto approvalResponse) {
    final OrderCancelledEvent orderCancelledEvent = orderApprovalSaga.rollback(approvalResponse);
    final UUID orderId = approvalResponse.getOrderId();
    final ValidationResult result = approvalResponse.getResult();
    log.info("Publishing order payment cancelled event for order id {} with failure messages {}",
             orderId,
             result.getLogOutput());
    orderCancelledPaymentRequestMessagePublisher.publish(orderCancelledEvent);
  }
}
