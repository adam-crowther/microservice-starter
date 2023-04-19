package com.acroteq.ticketing.payment.service.domain.entity;

import com.acroteq.ticketing.domain.entity.BaseEntity;
import com.acroteq.ticketing.domain.valueobject.CashValue;
import com.acroteq.ticketing.domain.valueobject.CustomerId;
import com.acroteq.ticketing.payment.service.domain.valueobject.CreditHistoryId;
import com.acroteq.ticketing.payment.service.domain.valueobject.TransactionType;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class CreditHistory extends BaseEntity<CreditHistoryId> {

  private final CustomerId customerId;
  private final CashValue credit;
  private final TransactionType transactionType;
}
