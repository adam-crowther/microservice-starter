package com.acroteq.ticketing.order.service.domain.exception;

import com.acroteq.domain.exception.DomainException;

public class FlightNumberMismatchException extends DomainException {

  private static final String I18N_CODE = "problem.flight.name.mismatch";
  private static final String MESSAGE = "The flight name '%s' does not match expected '%s'";

  private final String actual;
  private final String expected;

  public FlightNumberMismatchException(final String actual, final String expected) {
    super(String.format(MESSAGE, actual, expected));
    this.actual = actual;
    this.expected = expected;
  }

  @Override
  public String getCode() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    return new String[]{ actual, expected };
  }
}
