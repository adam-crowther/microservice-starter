package com.acroteq.ticketing.payment.service.data.access.customer.exception;

import com.acroteq.domain.exception.DomainException;

public class CustomerMissingIdException extends DomainException {

  private static final String I18N_CODE = "problem.customer.has.no.id";
  private static final String MESSAGE = "Customer has no id";

  public CustomerMissingIdException() {
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
