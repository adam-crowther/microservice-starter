package com.acroteq.ticketing.order.service.domain;

import com.acroteq.ticketing.domain.validation.ValidationResult;
import com.acroteq.ticketing.order.service.domain.dto.message.AirlineApprovalResponseDto;
import com.acroteq.ticketing.order.service.domain.event.OrderCancelledEvent;
import com.acroteq.ticketing.order.service.domain.ports.input.message.listener.airlineapproval.AirlineApprovalResponseMessageListener;
import com.acroteq.ticketing.order.service.domain.ports.output.message.publisher.payment.OrderCancelledPaymentRequestMessagePublisher;
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
  private final OrderCancelledPaymentRequestMessagePublisher messagePublisher;

  @Override
  @Transactional
  public void orderApproved(final AirlineApprovalResponseDto approvalResponse) {
    saga.process(approvalResponse);
    final Long orderId = approvalResponse.getOrderId();
    log.info("Order with order id {} is approved", orderId);
  }

  @Override
  @Transactional
  public void orderRejected(final AirlineApprovalResponseDto approvalResponse) {
    final OrderCancelledEvent orderCancelledEvent = saga.rollback(approvalResponse);
    final Long orderId = approvalResponse.getOrderId();
    final ValidationResult result = approvalResponse.getResult();
    log.info("Publishing order payment cancelled event for order id {} with failure messages {}",
             orderId,
             result.getLogOutput());
    messagePublisher.publish(orderCancelledEvent);
  }
}
