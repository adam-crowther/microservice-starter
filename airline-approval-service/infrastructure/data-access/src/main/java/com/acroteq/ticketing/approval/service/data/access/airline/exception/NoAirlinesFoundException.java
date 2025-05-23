package com.acroteq.ticketing.approval.service.data.access.airline.exception;

import com.acroteq.domain.exception.DomainException;

public class NoAirlinesFoundException extends DomainException {

  private static final String I18N_CODE = "problem.no.airlines.found";
  private static final String MESSAGE = "No Airlines Found";

  public NoAirlinesFoundException() {
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
