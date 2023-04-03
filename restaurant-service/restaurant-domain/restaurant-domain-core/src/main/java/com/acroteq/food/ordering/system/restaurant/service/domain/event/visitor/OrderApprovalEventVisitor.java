package com.acroteq.food.ordering.system.restaurant.service.domain.event.visitor;

import com.acroteq.food.ordering.system.restaurant.service.domain.event.OrderApprovedEvent;
import com.acroteq.food.ordering.system.restaurant.service.domain.event.OrderRejectedEvent;

public interface OrderApprovalEventVisitor {

  void visit(final OrderApprovedEvent event);

  void visit(final OrderRejectedEvent event);
}
