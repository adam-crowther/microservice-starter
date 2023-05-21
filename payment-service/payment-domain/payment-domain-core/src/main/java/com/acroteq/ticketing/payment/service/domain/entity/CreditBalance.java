package com.acroteq.ticketing.payment.service.domain.entity;

import com.acroteq.ticketing.domain.entity.PrimaryEntity;
import com.acroteq.ticketing.domain.valueobject.CashValue;
import com.acroteq.ticketing.payment.service.domain.valueobject.CreditBalanceId;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class CreditBalance extends PrimaryEntity<CreditBalanceId> {

  @NonNull
  private final CashValue totalCredit;
  @NonNull
  private final Customer customer;

  public CreditBalance addCredit(final CashValue amount) {
    final CashValue sum = totalCredit.add(amount);
    return toBuilder().totalCredit(sum)
                      .build();
  }

  public CreditBalance subtractCredit(final CashValue amount) {
    final CashValue difference = totalCredit.subtract(amount);
    return toBuilder().totalCredit(difference)
                      .build();
  }
}
