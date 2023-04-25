package com.acroteq.ticketing.payment.service.domain.exception;

import com.acroteq.ticketing.domain.exception.DomainException;
import com.acroteq.ticketing.domain.valueobject.CustomerId;

public class CreditEntrySaveFailedException extends DomainException {

  private static final String I18N_CODE = "problem.creditEntry.save.failed";
  private static final String MESSAGE = "Failed while saving credit entry: %s";

  private final CustomerId customerId;

  public CreditEntrySaveFailedException(final CustomerId customerId) {
    super(String.format(MESSAGE, customerId));
    this.customerId = customerId;
  }

  @Override
  public String getCode() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    return new String[]{ customerId.toString() };
  }
}
