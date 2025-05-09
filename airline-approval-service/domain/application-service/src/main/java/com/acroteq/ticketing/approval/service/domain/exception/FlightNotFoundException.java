package com.acroteq.ticketing.approval.service.domain.exception;

import com.acroteq.domain.exception.NotFoundException;
import com.acroteq.domain.valueobject.FlightId;

import java.util.Optional;

public class FlightNotFoundException extends NotFoundException {

  private static final String I18N_CODE = "problem.flight.not.found";
  private static final String MESSAGE = "Flight not found %s";

  private final FlightId flightId;
  private final String code;

  public FlightNotFoundException(final String code) {
    super(String.format(MESSAGE, code));
    this.flightId = null;
    this.code = code;
  }

  public FlightNotFoundException(final FlightId flightId) {
    super(String.format(MESSAGE, flightId));
    this.flightId = flightId;
    this.code = null;
  }

  @Override
  public String getUserName() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    final String parameter = Optional.ofNullable(flightId)
                                     .map(Object::toString)
                                     .orElse(code);
    return new String[]{ parameter };
  }
}
