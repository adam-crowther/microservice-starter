package com.acroteq.ticketing.airline.service.domain.exception;

import com.acroteq.domain.exception.DomainException;
import com.acroteq.domain.valueobject.AirlineId;

public class AirlineUpdateFailedException extends DomainException {

  private static final String I18N_CODE = "problem.airline.update.failed";
  private static final String MESSAGE = "Failed while updating airline: %s";

  private final AirlineId airlineId;

  public AirlineUpdateFailedException(final AirlineId airlineId) {
    super(String.format(MESSAGE, airlineId));
    this.airlineId = airlineId;
  }

  @Override
  public String getUserName() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    return new String[]{ airlineId.getValue().toString() };
  }
}
