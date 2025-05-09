package com.acroteq.ticketing.airline.service.domain.exception;

import com.acroteq.domain.exception.DomainException;

public class AirlineValidationException extends DomainException {

  private static final String I18N_CODE = "problem.airline.validation";
  private static final String MESSAGE = "Airline field %s must not be empty or null";

  private final String fieldName;

  public AirlineValidationException(final String fieldName) {
    super(String.format(MESSAGE, fieldName));
    this.fieldName = fieldName;
  }

  @Override
  public String getUserName() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    return new String[]{ fieldName };
  }
}
