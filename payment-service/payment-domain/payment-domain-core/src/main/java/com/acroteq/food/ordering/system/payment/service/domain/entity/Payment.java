package com.acroteq.food.ordering.system.payment.service.domain.entity;

import com.acroteq.food.ordering.system.domain.entity.AggregateRoot;
import com.acroteq.food.ordering.system.domain.validation.ValidationResult;
import com.acroteq.food.ordering.system.domain.valueobject.CashValue;
import com.acroteq.food.ordering.system.domain.valueobject.CustomerId;
import com.acroteq.food.ordering.system.domain.valueobject.OrderId;
import com.acroteq.food.ordering.system.domain.valueobject.PaymentStatus;
import com.acroteq.food.ordering.system.payment.service.domain.valueobject.PaymentId;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

import static com.acroteq.food.ordering.system.domain.validation.ValidationResult.fail;
import static com.acroteq.food.ordering.system.domain.validation.ValidationResult.pass;
import static java.time.ZoneOffset.UTC;
import static java.time.ZonedDateTime.now;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class Payment extends AggregateRoot<PaymentId> {

  private final OrderId orderId;
  private final CustomerId customerId;
  private final CashValue value;

  private PaymentStatus paymentStatus;
  private ZonedDateTime createdDateTime;

  public void initializePayment() {
    setId(PaymentId.random());
    createdDateTime = now(UTC);
  }

  public ValidationResult validatePayment() {
    if (value == null || !value.isGreaterThanZero()) {
      return fail("Total price must be greater than zero!");
    }

    return pass();
  }

  public void updateStatus(final PaymentStatus paymentStatus) {
    this.paymentStatus = paymentStatus;
  }
}
