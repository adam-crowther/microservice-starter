package com.acroteq.ticketing.payment.service.domain.entity;

import com.acroteq.domain.entity.PrimaryEntity;
import com.acroteq.domain.valueobject.CashValue;
import com.acroteq.ticketing.payment.service.domain.valueobject.CreditChangeId;
import com.acroteq.ticketing.payment.service.domain.valueobject.CreditChangeType;
import com.acroteq.ticketing.payment.service.domain.valueobject.TransactionType;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class CreditChange extends PrimaryEntity<CreditChangeId> {

  @NonNull
  private final Customer customer;
  @NonNull
  private final CashValue creditDelta;
  @NonNull
  private final TransactionType transactionType;
  @NonNull
  private final CreditChangeType creditChangeType;
}
