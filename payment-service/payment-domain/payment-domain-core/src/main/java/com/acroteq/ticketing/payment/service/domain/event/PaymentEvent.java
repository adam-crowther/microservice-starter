package com.acroteq.ticketing.payment.service.domain.event;

import static com.acroteq.ticketing.domain.validation.ValidationResult.pass;

import com.acroteq.ticketing.domain.event.SagaEvent;
import com.acroteq.ticketing.domain.validation.ValidationResult;
import com.acroteq.ticketing.payment.service.domain.entity.Payment;
import com.acroteq.ticketing.payment.service.domain.event.visitor.PaymentEventVisitor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
public abstract class PaymentEvent extends SagaEvent {

  @NonNull
  private final Payment payment;
  @Builder.Default
  private final ValidationResult result = pass();

  public static PolymorphicPaymentEventBuilder polymorphicBuilder() {
    return new PolymorphicPaymentEventBuilder();
  }

  public abstract void accept(PaymentEventVisitor visitor);
}
