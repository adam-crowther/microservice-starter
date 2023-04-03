package com.acroteq.food.ordering.system.payment.service.domain.event;

import com.acroteq.food.ordering.system.payment.service.domain.event.visitor.PaymentEventVisitor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@ToString
@SuperBuilder(toBuilder = true)
public class PaymentFailedEvent extends PaymentEvent {

  @Override
  public void accept(final PaymentEventVisitor visitor) {
    visitor.visit(this);
  }
}
