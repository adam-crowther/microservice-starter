package com.acroteq.ticketing.payment.service.domain.event;

import com.acroteq.ticketing.payment.service.domain.event.visitor.PaymentEventVisitor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@ToString
@SuperBuilder(toBuilder = true)
public class PaymentCompletedEvent extends PaymentEvent {

  @Override
  public void accept(final PaymentEventVisitor visitor) {
    visitor.visit(this);
  }
}
