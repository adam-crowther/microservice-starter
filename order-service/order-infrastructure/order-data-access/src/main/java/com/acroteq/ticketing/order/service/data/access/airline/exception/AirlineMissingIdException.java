package com.acroteq.ticketing.order.service.data.access.airline.exception;

import com.acroteq.ticketing.domain.exception.DomainException;

public class AirlineMissingIdException extends DomainException {

  private static final String I18N_CODE = "problem.airline.has.no.id";
  private static final String MESSAGE = "Airline has no id";

  public AirlineMissingIdException() {
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
