package com.acroteq.food.ordering.system.payment.service.domain.exception;

import com.acroteq.food.ordering.system.domain.exception.DomainException;
import com.acroteq.food.ordering.system.payment.service.domain.valueobject.CreditHistoryId;

public class CreditHistorySaveFailedException extends DomainException {

  private static final String I18N_CODE = "problem.credit.history.save.failed";
  private static final String MESSAGE = "Failed while saving credit history: %s";

  private final CreditHistoryId creditHistoryId;

  public CreditHistorySaveFailedException(final CreditHistoryId creditHistoryId) {
    super(String.format(MESSAGE, creditHistoryId));
    this.creditHistoryId = creditHistoryId;
  }

  @Override
  public String getCode() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    return new String[]{ creditHistoryId.toString() };
  }
}
