package com.acroteq.ticketing.order.service.domain.exception;

import com.acroteq.ticketing.domain.exception.DomainException;
import com.acroteq.ticketing.domain.valueobject.AirlineId;

public class AirlineNotFoundException extends DomainException {

  private static final String I18N_CODE = "problem.airline.not.found";
  private static final String MESSAGE = "Airline not found %s";

  private final AirlineId airlineId;

  public AirlineNotFoundException(final AirlineId airlineId) {
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
