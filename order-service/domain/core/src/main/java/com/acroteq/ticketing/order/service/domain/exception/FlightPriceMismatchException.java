package com.acroteq.ticketing.order.service.domain.exception;

import com.acroteq.domain.exception.DomainException;
import com.acroteq.domain.valueobject.CashValue;

public class FlightPriceMismatchException extends DomainException {

  private static final String I18N_CODE = "problem.flight.price.mismatch";
  private static final String MESSAGE = "The flight price '%s' does not match expected '%s'";

  private final CashValue actual;
  private final CashValue expected;

  public FlightPriceMismatchException(final CashValue actual, final CashValue expected) {
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
    return new String[]{ actual.toString(), expected.toString() };
  }
}
