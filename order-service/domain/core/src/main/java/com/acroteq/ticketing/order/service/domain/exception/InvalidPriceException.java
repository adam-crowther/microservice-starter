package com.acroteq.ticketing.order.service.domain.exception;

import com.acroteq.domain.exception.DomainException;

public class InvalidPriceException extends DomainException {

  private static final String I18N_CODE = "problem.invalid.price";
  private static final String MESSAGE = "Flight price must be greater than zero.";

  public InvalidPriceException() {
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
