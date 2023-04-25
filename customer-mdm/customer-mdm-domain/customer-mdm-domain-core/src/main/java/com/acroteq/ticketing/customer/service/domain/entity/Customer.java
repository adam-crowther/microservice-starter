package com.acroteq.ticketing.customer.service.domain.entity;

import static com.acroteq.ticketing.precondition.Precondition.checkPrecondition;

import com.acroteq.ticketing.customer.service.domain.exception.CustomerCreditLimitException;
import com.acroteq.ticketing.domain.entity.AggregateRoot;
import com.acroteq.ticketing.domain.valueobject.CashValue;
import com.acroteq.ticketing.domain.valueobject.CustomerId;
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
    checkPrecondition(creditLimit.isGreaterThanOrEqualToZero(), CustomerCreditLimitException::new, creditLimit);
  }
}
