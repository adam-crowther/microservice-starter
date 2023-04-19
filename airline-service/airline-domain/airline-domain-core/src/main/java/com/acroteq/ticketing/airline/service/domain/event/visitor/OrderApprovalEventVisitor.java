package com.acroteq.ticketing.airline.service.domain.event.visitor;

import com.acroteq.ticketing.airline.service.domain.event.OrderApprovedEvent;
import com.acroteq.ticketing.airline.service.domain.event.OrderRejectedEvent;

public interface OrderApprovalEventVisitor {

  void visit(OrderApprovedEvent event);

  void visit(OrderRejectedEvent event);
}
