package com.acroteq.ticketing.order.service.domain.exception;

import com.acroteq.domain.exception.NotFoundException;
import com.acroteq.domain.valueobject.FlightId;

public class FlightNotFoundException extends NotFoundException {

  private static final String I18N_CODE = "problem.flight.not.found";
  private static final String MESSAGE = "Flight not found: ";

  private final FlightId flightId;

  public FlightNotFoundException(final FlightId flightId) {
    super(MESSAGE + flightId);
    this.flightId = flightId;
  }

  @Override
  public String getUserName() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    return new String[]{ flightId.toString() };
  }
}
