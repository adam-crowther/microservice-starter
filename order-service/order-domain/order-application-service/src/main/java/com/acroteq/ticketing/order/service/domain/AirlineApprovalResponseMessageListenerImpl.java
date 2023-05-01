package com.acroteq.ticketing.order.service.domain;

import com.acroteq.ticketing.domain.validation.ValidationResult;
import com.acroteq.ticketing.order.service.domain.dto.message.AirlineApprovalApprovedResponseDto;
import com.acroteq.ticketing.order.service.domain.dto.message.AirlineApprovalRejectedResponseDto;
import com.acroteq.ticketing.order.service.domain.event.OrderCancelledEvent;
import com.acroteq.ticketing.order.service.domain.ports.input.message.listener.airlineapproval.AirlineApprovalResponseMessageListener;
import com.acroteq.ticketing.order.service.domain.ports.output.message.publisher.payment.PaymentCancelRequestMessagePublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Slf4j
@RequiredArgsConstructor
@Validated
@Service
public class AirlineApprovalResponseMessageListenerImpl implements AirlineApprovalResponseMessageListener {

  private final OrderApprovalSaga saga;
  private final PaymentCancelRequestMessagePublisher messagePublisher;

  @Override
  @Transactional
  public void orderApproved(final AirlineApprovalApprovedResponseDto response) {
    saga.process(response);
    final Long orderId = response.getOrderId();
    log.info("Order with order id {} is approved", orderId);
  }

  @Override
  @Transactional
  public void orderRejected(final AirlineApprovalRejectedResponseDto response) {
    final OrderCancelledEvent orderCancelledEvent = saga.rollback(response);
    final Long orderId = response.getOrderId();
    final ValidationResult result = response.getResult();
    log.info("Publishing order payment cancelled event for order id {} with failure messages {}",
             orderId,
             result.getLogOutput());
    messagePublisher.publish(orderCancelledEvent);
  }
}
