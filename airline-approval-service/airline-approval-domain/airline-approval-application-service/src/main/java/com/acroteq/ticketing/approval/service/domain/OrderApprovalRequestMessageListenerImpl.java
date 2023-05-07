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

  // This is OK in a message listener.  We have to catch, log and rethrow everything, that's the point.
  @SuppressWarnings("PMD.AvoidCatchingGenericException")
  @Transactional
  @Override
  public void checkOrder(final AirlineApprovalRequestDto request) {
    final Long orderId = request.getOrderId();
    try {
      log.info("Checking order with id {}", orderId);
      final OrderApprovalEvent orderApprovalEvent = requestProcessor.checkOrder(request);
      orderApprovalEvent.accept(eventPublisher);
      log.debug("Finished checking order with id {}", orderId);
    } catch (final Exception exception) {
      log.error("Exception while checking order with id {}. Sending REJECTED response.", orderId, exception);
      final OrderApprovalEvent rejectedEvent = exceptionEventFactory.createOrderRejectedEvent(request, exception);
      rejectedEvent.accept(eventPublisher);
    }
  }
}
