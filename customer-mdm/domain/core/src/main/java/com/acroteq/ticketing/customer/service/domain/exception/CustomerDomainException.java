package com.acroteq.ticketing.customer.service.domain.exception;

import com.acroteq.domain.exception.DomainException;

public class CustomerDomainException extends DomainException {

  private static final String I18N_CODE = "problem.customer.error";
  private static final String MESSAGE = "Customer error";

  public CustomerDomainException() {
    super(MESSAGE);
  }

  @Override
  public String getUserName() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    return new String[0];
  }
}
