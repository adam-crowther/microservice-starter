package com.acroteq.ticketing.airline.service.domain.exception;

import com.acroteq.domain.exception.DomainException;

public class AirlineDomainException extends DomainException {

  private static final String I18N_CODE = "problem.airline.error";
  private static final String MESSAGE = "Airline Domain Exception";

  public AirlineDomainException() {
    super(MESSAGE);
  }

  @Override
  public String getUserName() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    return new String[0];
  }
}
