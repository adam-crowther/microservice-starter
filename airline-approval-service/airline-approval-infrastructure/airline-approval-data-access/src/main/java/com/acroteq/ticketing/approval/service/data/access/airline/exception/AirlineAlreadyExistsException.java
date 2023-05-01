package com.acroteq.ticketing.approval.service.data.access.airline.exception;

import com.acroteq.ticketing.domain.exception.DomainException;
import com.acroteq.ticketing.domain.valueobject.AirlineId;

public class AirlineAlreadyExistsException extends DomainException {

  private static final String I18N_CODE = "problem.airline.already.exists";
  private static final String MESSAGE = "Airline %s already exists";

  private final AirlineId airlineId;

  public AirlineAlreadyExistsException(final AirlineId airlineId) {
    super(String.format(MESSAGE, airlineId));
    this.airlineId = airlineId;
  }

  @Override
  public String getCode() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    return new String[]{ airlineId.toString() };
  }
}
