package com.acroteq.ticketing.customer.service.domain.exception;

import com.acroteq.ticketing.domain.exception.DomainException;

public class CustomerValidationException extends DomainException {

  private static final String I18N_CODE = "problem.customer.validation";
  private static final String MESSAGE = "Customer field %s must not be empty or null";

  private final String fieldName;

  public CustomerValidationException(final String fieldName) {
    super(String.format(MESSAGE, fieldName));
    this.fieldName = fieldName;
  }

  @Override
  public String getCode() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    return new String[]{ fieldName };
  }
}
