package com.acroteq.ticketing.approval.service.domain.event.order.visitor;

import com.acroteq.ticketing.approval.service.domain.event.order.OrderApprovedEvent;
import com.acroteq.ticketing.approval.service.domain.event.order.OrderRejectedEvent;

public interface OrderApprovalEventVisitor {

  void visit(OrderApprovedEvent event);

  void visit(OrderRejectedEvent event);
}
