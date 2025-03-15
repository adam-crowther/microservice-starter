package com.acroteq.ticketing.customer.service.domain.entity;

import static com.acroteq.domain.valueobject.CashValue.ZERO;
import static com.acroteq.precondition.Precondition.checkPrecondition;

import com.acroteq.domain.entity.AggregateRoot;
import com.acroteq.domain.valueobject.CashValue;
import com.acroteq.domain.valueobject.CustomerId;
import com.acroteq.ticketing.customer.service.domain.exception.CustomerCreditLimitException;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class Customer extends AggregateRoot<CustomerId> {

  @NonNull
  private final String userName;
  @NonNull
  private final String firstName;
  @NonNull
  private final String lastName;
  @NonNull
  private final CashValue creditLimit;

  public void validate() {
    checkPrecondition(creditLimit.isGreaterThanOrEqualTo(ZERO), CustomerCreditLimitException::new, creditLimit);
  }
}
