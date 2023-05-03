package com.acroteq.ticketing.payment.service.domain.event;

import com.acroteq.ticketing.payment.service.domain.event.visitor.PaymentEventVisitor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder(toBuilder = true)
public class PaymentCancelledEvent extends PaymentEvent {

  @Override
  public void accept(final PaymentEventVisitor visitor) {
    visitor.visit(this);
  }
}
