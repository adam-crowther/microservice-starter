package com.acroteq.ticketing.airline.service.domain.exception;

import com.acroteq.ticketing.domain.exception.DomainException;

public class AirlineApplicationServiceException extends DomainException {

  private static final String I18N_CODE = "problem.airline.application.error";
  private static final String MESSAGE = "Airline Application Error";

  public AirlineApplicationServiceException() {
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
