package com.acroteq.food.ordering.system.payment.service.domain.entity;

import com.acroteq.food.ordering.system.domain.entity.BaseEntity;
import com.acroteq.food.ordering.system.domain.valueobject.CashValue;
import com.acroteq.food.ordering.system.domain.valueobject.CustomerId;
import com.acroteq.food.ordering.system.payment.service.domain.valueobject.CreditEntryId;
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
