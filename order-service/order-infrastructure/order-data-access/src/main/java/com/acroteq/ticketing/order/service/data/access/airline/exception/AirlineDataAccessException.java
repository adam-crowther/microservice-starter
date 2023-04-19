package com.acroteq.ticketing.order.service.data.access.airline.exception;

import com.acroteq.ticketing.domain.exception.DomainException;

public class AirlineDataAccessException extends DomainException {

  private static final String I18N_CODE = "problem.airline.data.access.exception";
  private static final String MESSAGE = "No airline found";

  public AirlineDataAccessException() {
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
