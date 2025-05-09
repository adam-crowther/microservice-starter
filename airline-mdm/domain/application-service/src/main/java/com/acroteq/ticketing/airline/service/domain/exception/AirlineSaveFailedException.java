package com.acroteq.ticketing.airline.service.domain.exception;

import com.acroteq.domain.exception.DomainException;

public class AirlineSaveFailedException extends DomainException {

  private static final String I18N_CODE = "problem.airline.save.failed";
  private static final String MESSAGE_WITHOUT_ID = "Failed while saving airline";

  public AirlineSaveFailedException() {
    super(MESSAGE_WITHOUT_ID);
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
