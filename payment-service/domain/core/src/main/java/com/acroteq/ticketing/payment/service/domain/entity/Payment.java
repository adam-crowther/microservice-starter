package com.acroteq.ticketing.payment.service.domain.entity;

import static com.acroteq.domain.validation.ValidationResult.fail;
import static com.acroteq.domain.validation.ValidationResult.pass;
import static com.acroteq.domain.valueobject.CashValue.ZERO;
import static com.acroteq.domain.valueobject.PaymentStatus.COMPLETED;

import com.acroteq.domain.entity.AggregateRoot;
import com.acroteq.domain.validation.ValidationResult;
import com.acroteq.domain.valueobject.CashValue;
import com.acroteq.domain.valueobject.OrderId;
import com.acroteq.domain.valueobject.PaymentId;
import com.acroteq.domain.valueobject.PaymentStatus;
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
  private final Customer customer;
  @NonNull
  private final CashValue value;

  @NonNull
  private final PaymentStatus status;

  public ValidationResult validate() {
    final ValidationResult result;
    if (value.isGreaterThan(ZERO)) {
      result = pass();
    } else {
      result = fail("Total price must be greater than zero");
    }

    return result;
  }

  public ValidationResult cancel() {
    final ValidationResult result;
    if (status == COMPLETED) {
      result = pass();
    } else {
      result = fail("Only COMPLETED payments can be cancelled");
    }

    return result;
  }
}
