package com.acroteq.ticketing.approval.service.domain.exception;

import com.acroteq.domain.exception.DomainException;

public class FlightNotFoundException extends DomainException {

  private static final String I18N_CODE = "problem.flight.not.found";
  private static final String MESSAGE = "Flight not found %s";

  private final String key;

  public FlightNotFoundException(final String key) {
    super(String.format(MESSAGE, key));
    this.key = key;
  }

  @Override
  public String getCode() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    return new String[]{ key };
  }
}
