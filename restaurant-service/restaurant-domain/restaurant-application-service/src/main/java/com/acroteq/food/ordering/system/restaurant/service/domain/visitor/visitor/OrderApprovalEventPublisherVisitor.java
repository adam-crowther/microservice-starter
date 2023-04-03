package com.acroteq.food.ordering.system.restaurant.service.domain.visitor.visitor;

import com.acroteq.food.ordering.system.domain.event.publisher.DomainEventPublisher;
import com.acroteq.food.ordering.system.restaurant.service.domain.event.OrderApprovalEvent;
import com.acroteq.food.ordering.system.restaurant.service.domain.event.OrderApprovedEvent;
import com.acroteq.food.ordering.system.restaurant.service.domain.event.OrderRejectedEvent;
import com.acroteq.food.ordering.system.restaurant.service.domain.event.visitor.OrderApprovalEventVisitor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderApprovalEventPublisherVisitor implements OrderApprovalEventVisitor {

  private final DomainEventPublisher<OrderApprovedEvent> orderApprovedEventDomainEventPublisher;
  private final DomainEventPublisher<OrderRejectedEvent> orderRejectedEventDomainEventPublisher;

  public void visit(final OrderApprovedEvent event) {
    logEvent("approved", event);
    orderApprovedEventDomainEventPublisher.publish(event);
  }


  public void visit(final OrderRejectedEvent event) {
    logEvent("rejected", event);
    orderRejectedEventDomainEventPublisher.publish(event);
  }

  private void logEvent(final String eventType, final OrderApprovalEvent event) {
    log.info("Publishing order {} event with order id: {}",
             eventType,
             event.getOrderApproval()
                  .getOrderId());
  }
}
