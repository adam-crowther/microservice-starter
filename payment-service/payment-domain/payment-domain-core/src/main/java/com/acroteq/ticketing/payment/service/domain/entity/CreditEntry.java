package com.acroteq.ticketing.payment.service.domain.entity;

import com.acroteq.ticketing.domain.entity.BaseEntity;
import com.acroteq.ticketing.domain.valueobject.CashValue;
import com.acroteq.ticketing.domain.valueobject.CustomerId;
import com.acroteq.ticketing.payment.service.domain.valueobject.CreditEntryId;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class CreditEntry extends BaseEntity<CreditEntryId> {

  private final CustomerId customerId;
  private CashValue totalCredit;

  public void addCredit(final CashValue amount) {
    totalCredit = totalCredit.add(amount);
  }

  public void subtractCredit(final CashValue amount) {
    totalCredit = totalCredit.subtract(amount);
  }
}
