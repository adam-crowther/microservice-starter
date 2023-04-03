package com.acroteq.food.ordering.system.payment.service.domain.event;

import com.acroteq.food.ordering.system.domain.event.DomainEvent;
import com.acroteq.food.ordering.system.domain.validation.ValidationResult;
import com.acroteq.food.ordering.system.payment.service.domain.entity.Payment;
import com.acroteq.food.ordering.system.payment.service.domain.event.visitor.PaymentEventVisitor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import static com.acroteq.food.ordering.system.domain.validation.ValidationResult.pass;

@Getter
@ToString
@SuperBuilder(toBuilder = true)
public abstract class PaymentEvent extends DomainEvent<Payment> {

  private final Payment payment;

  @Builder.Default
  private final ValidationResult result = pass();

  public abstract void accept(final PaymentEventVisitor visitor);
}
