package com.acroteq.ticketing.approval.service.domain;

import com.acroteq.ticketing.approval.service.domain.dto.AirlineApprovalRequestDto;
import com.acroteq.ticketing.approval.service.domain.event.order.OrderApprovalEvent;
import com.acroteq.ticketing.approval.service.domain.ports.input.message.listener.order.OrderApprovalRequestMessageListener;
import com.acroteq.ticketing.approval.service.domain.processor.AirlineApprovalExceptionEventFactory;
import com.acroteq.ticketing.approval.service.domain.processor.AirlineApprovalRequestProcessor;
import com.acroteq.ticketing.approval.service.domain.visitor.OrderApprovalEventPublisherVisitor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderApprovalRequestMessageListenerImpl implements OrderApprovalRequestMessageListener {

  private final AirlineApprovalRequestProcessor requestProcessor;
  private final AirlineApprovalExceptionEventFactory exceptionEventFactory;
  private final OrderApprovalEventPublisherVisitor eventPublisher;

  @Transactional
  @Override
  public void checkOrder(final AirlineApprovalRequestDto airlineApprovalRequest) {
    final Long orderId = airlineApprovalRequest.getOrderId();
    try {
      log.info("Checking order with id {}", orderId);
      final OrderApprovalEvent orderApprovalEvent = requestProcessor.checkOrder(airlineApprovalRequest);
      orderApprovalEvent.accept(eventPublisher);
      log.debug("Checking order with id {}", orderId);
    } catch (final Exception exception) {
      log.error("Exception while checking order with id {}.  Sending REJECTED response.", orderId);
      final OrderApprovalEvent rejectedEvent = exceptionEventFactory.createOrderRejectedEvent(airlineApprovalRequest,
                                                                                              exception);
      rejectedEvent.accept(eventPublisher);
    }
  }
}
