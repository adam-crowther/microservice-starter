package com.acroteq.ticketing.payment.service.domain.entity;

import com.acroteq.ticketing.domain.entity.BaseEntity;
import com.acroteq.ticketing.domain.valueobject.CashValue;
import com.acroteq.ticketing.domain.valueobject.CustomerId;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class CreditEntry extends BaseEntity<CustomerId> {

  @NonNull
  private final CashValue totalCredit;

  public CreditEntry addCredit(final CashValue amount) {
    final CashValue sum = totalCredit.add(amount);
    return toBuilder().totalCredit(sum)
                      .build();
  }

  public CreditEntry subtractCredit(final CashValue amount) {
    final CashValue difference = totalCredit.subtract(amount);
    return toBuilder().totalCredit(difference)
                      .build();
  }
}
