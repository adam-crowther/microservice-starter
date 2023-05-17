package com.acroteq.ticketing.payment.service.domain.exception;

import com.acroteq.ticketing.domain.exception.DomainException;
import com.acroteq.ticketing.domain.valueobject.CustomerId;

public class CreditBalanceSaveFailedException extends DomainException {

  private static final String I18N_CODE = "problem.creditBalance.save.failed";
  private static final String MESSAGE = "Failed while saving credit balance: %s";

  private final CustomerId customerId;

  public CreditBalanceSaveFailedException(final CustomerId customerId) {
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
