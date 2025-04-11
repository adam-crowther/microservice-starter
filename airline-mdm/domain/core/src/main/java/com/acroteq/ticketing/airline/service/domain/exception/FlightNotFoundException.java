package com.acroteq.ticketing.airline.service.domain.exception;

import com.acroteq.domain.exception.DomainException;

public class FlightNotFoundException extends DomainException {

  private static final String I18N_CODE = "problem.flight.not.found";
  private static final String MESSAGE = "Flight not found %s";

  private final String code;

  public FlightNotFoundException(final String code) {
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
