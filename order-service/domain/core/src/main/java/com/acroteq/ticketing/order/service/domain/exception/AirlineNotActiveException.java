package com.acroteq.ticketing.order.service.domain.exception;

import com.acroteq.domain.exception.DomainException;
import com.acroteq.domain.valueobject.AirlineId;

public class AirlineNotActiveException extends DomainException {

  private static final String I18N_CODE = "problem.airline.not.active";
  private static final String MESSAGE = "Airline %s is currently not active";

  private final AirlineId airlineId;

  public AirlineNotActiveException(final AirlineId airlineId) {
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
