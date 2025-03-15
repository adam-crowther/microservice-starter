package com.acroteq.ticketing.payment.service.domain.entity;

import static com.acroteq.domain.valueobject.CashValue.ZERO;

import com.acroteq.domain.entity.ReplicatedEntity;
import com.acroteq.domain.valueobject.CashValue;
import com.acroteq.domain.valueobject.CustomerId;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class Customer extends ReplicatedEntity<CustomerId> {

  @NonNull
  private final CashValue creditLimit;

  public Customer withZeroCreditLimit() {
    return toBuilder().creditLimit(ZERO)
                      .build();
  }
}
