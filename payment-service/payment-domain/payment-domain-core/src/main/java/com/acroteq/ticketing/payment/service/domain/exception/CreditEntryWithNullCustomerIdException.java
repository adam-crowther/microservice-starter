package com.acroteq.ticketing.payment.service.domain.exception;

import com.acroteq.ticketing.domain.exception.DomainException;

public class CreditEntryWithNullCustomerIdException extends DomainException {

  private static final String I18N_CODE = "problem.credit.entry.missing.customer.id.error";
  private static final String MESSAGE = "Credit entry has null customerId";

  public CreditEntryWithNullCustomerIdException() {
    super(MESSAGE);
  }

  @Override
  public String getCode() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    return new String[0];
  }
}
