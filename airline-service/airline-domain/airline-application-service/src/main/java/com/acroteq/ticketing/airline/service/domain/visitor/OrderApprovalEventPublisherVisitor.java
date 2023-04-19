package com.acroteq.ticketing.airline.service.domain.visitor;

import com.acroteq.ticketing.airline.service.domain.event.OrderApprovalEvent;
import com.acroteq.ticketing.airline.service.domain.event.OrderApprovedEvent;
import com.acroteq.ticketing.airline.service.domain.event.OrderRejectedEvent;
import com.acroteq.ticketing.airline.service.domain.event.visitor.OrderApprovalEventVisitor;
import com.acroteq.ticketing.domain.event.publisher.DomainEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderApprovalEventPublisherVisitor implements OrderApprovalEventVisitor {

  private final DomainEventPublisher<OrderApprovedEvent> orderApprovedEventPublisher;
  private final DomainEventPublisher<OrderRejectedEvent> orderRejectedEventPublisher;

  @Override
  public void visit(final OrderApprovedEvent event) {
    logEvent("approved", event);
    orderApprovedEventPublisher.publish(event);
  }


  @Override
  public void visit(final OrderRejectedEvent event) {
    logEvent("rejected", event);
    orderRejectedEventPublisher.publish(event);
  }

  private void logEvent(final String eventType, final OrderApprovalEvent event) {
    log.info("Publishing order {} event with order id: {}", eventType, event.getOrderId());
  }
}
