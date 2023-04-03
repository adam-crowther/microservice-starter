package com.acroteq.food.ordering.system.payment.service.domain.event.visitor;

import com.acroteq.food.ordering.system.payment.service.domain.event.PaymentCancelledEvent;
import com.acroteq.food.ordering.system.payment.service.domain.event.PaymentCompletedEvent;
import com.acroteq.food.ordering.system.payment.service.domain.event.PaymentFailedEvent;

public interface PaymentEventVisitor {

  void visit(final PaymentCompletedEvent event);

  void visit(final PaymentCancelledEvent event);

  void visit(final PaymentFailedEvent event);
}
