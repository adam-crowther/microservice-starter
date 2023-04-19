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
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class Payment extends AggregateRoot<PaymentId> {

  private final OrderId orderId;
  private final CustomerId customerId;
  private final CashValue value;

  private final PaymentStatus paymentStatus;
  private final ZonedDateTime createdDateTime;

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
