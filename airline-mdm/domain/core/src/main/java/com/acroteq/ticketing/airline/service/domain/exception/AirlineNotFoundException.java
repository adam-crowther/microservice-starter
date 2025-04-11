package com.acroteq.ticketing.airline.service.domain.exception;

import com.acroteq.domain.exception.DomainException;

public class AirlineNotFoundException extends DomainException {

  private static final String I18N_CODE = "problem.airline.not.found";
  private static final String MESSAGE = "Airline not found %s";

  private final String code;

  public AirlineNotFoundException(final String code) {
    super(String.format(MESSAGE, code));
    this.code = code;
  }

  @Override
  public String getCode() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    return new String[]{ code };
  }
}
