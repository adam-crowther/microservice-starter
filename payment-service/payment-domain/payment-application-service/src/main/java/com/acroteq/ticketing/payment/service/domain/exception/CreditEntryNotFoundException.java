package com.acroteq.ticketing.payment.service.domain.exception;


import com.acroteq.ticketing.domain.exception.DomainException;
import com.acroteq.ticketing.domain.valueobject.CustomerId;

public class CreditEntryNotFoundException extends DomainException {

  private static final String I18N_CODE = "problem.credit.entry.not.found";
  private static final String MESSAGE = "Credit entry not found: ";

  private final CustomerId customerId;

  public CreditEntryNotFoundException(final CustomerId customerId) {
    super(MESSAGE + customerId);
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