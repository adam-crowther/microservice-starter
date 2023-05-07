package com.acroteq.ticketing.payment.service.domain.entity;

import static com.acroteq.ticketing.domain.valueobject.CashValue.ZERO;

import com.acroteq.ticketing.domain.entity.MasterEntity;
import com.acroteq.ticketing.domain.valueobject.CashValue;
import com.acroteq.ticketing.payment.service.domain.valueobject.CreditEntryId;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class CreditEntry extends MasterEntity<CreditEntryId> {

  @NonNull
  private final CashValue totalCredit;
  @NonNull
  private final Customer customer;

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

  public CreditEntry zeroCredit() {
    return toBuilder().totalCredit(ZERO)
                      .build();
  }
}
