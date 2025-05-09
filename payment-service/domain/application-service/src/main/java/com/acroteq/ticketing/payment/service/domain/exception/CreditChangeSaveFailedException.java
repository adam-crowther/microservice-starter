package com.acroteq.ticketing.payment.service.domain.exception;

import com.acroteq.domain.exception.DomainException;
import com.acroteq.ticketing.payment.service.domain.valueobject.CreditChangeId;

public class CreditChangeSaveFailedException extends DomainException {

  private static final String I18N_CODE = "problem.credit.change.save.failed";
  private static final String MESSAGE = "Failed while saving credit change: %s";

  private final CreditChangeId creditChangeId;

  public CreditChangeSaveFailedException(final CreditChangeId creditChangeId) {
    super(String.format(MESSAGE, creditChangeId));
    this.creditChangeId = creditChangeId;
  }

  @Override
  public String getUserName() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    return new String[]{ creditChangeId.toString() };
  }
}
