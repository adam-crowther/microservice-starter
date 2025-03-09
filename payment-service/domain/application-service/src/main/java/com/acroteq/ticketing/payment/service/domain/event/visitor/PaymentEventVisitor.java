package com.acroteq.ticketing.payment.service.domain.event.visitor;

import com.acroteq.ticketing.payment.service.domain.event.PaymentCancelledEvent;
import com.acroteq.ticketing.payment.service.domain.event.PaymentCompletedEvent;
import com.acroteq.ticketing.payment.service.domain.event.PaymentFailedEvent;

public interface PaymentEventVisitor {

  void visit(PaymentCompletedEvent event);

  void visit(PaymentCancelledEvent event);

  void visit(PaymentFailedEvent event);
}
