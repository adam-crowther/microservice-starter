package com.acroteq.ticketing.payment.service.domain.entity;

import static com.acroteq.ticketing.domain.validation.ValidationResult.fail;
import static com.acroteq.ticketing.domain.validation.ValidationResult.pass;

import com.acroteq.ticketing.domain.entity.AggregateRoot;
import com.acroteq.ticketing.domain.validation.ValidationResult;
import com.acroteq.ticketing.domain.valueobject.CashValue;
import com.acroteq.ticketing.domain.valueobject.CustomerId;
import com.acroteq.ticketing.domain.valueobject.OrderId;
import com.acroteq.ticketing.domain.valueobject.PaymentId;
import com.acroteq.ticketing.domain.valueobject.PaymentStatus;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class Payment extends AggregateRoot<PaymentId> {

  @NonNull
  private final OrderId orderId;
  @NonNull
  private final Long orderVersion;
  @NonNull
  private final CustomerId customerId;
  @NonNull
  private final CashValue value;

  @NonNull
  private final PaymentStatus paymentStatus;

  public ValidationResult validatePayment() {
    final ValidationResult result;
    if (value == null || !value.isGreaterThanZero()) {
      result = fail("Total price must be greater than zero!");
    } else {
      result = pass();
    }

    return result;
  }
}
