package com.acroteq.ticketing.approval.service.domain.exception;

import com.acroteq.domain.exception.NotFoundException;
import com.acroteq.domain.valueobject.AirlineId;

import java.util.Optional;

public class AirlineNotFoundException extends NotFoundException {

  private static final String I18N_CODE = "problem.airline.not.found";
  private static final String MESSAGE = "Airline not found %s";

  private final AirlineId airlineId;
  private final String code;

  public AirlineNotFoundException(final String code) {
    super(String.format(MESSAGE, code));
    this.airlineId = null;
    this.code = code;
  }

  public AirlineNotFoundException(final AirlineId airlineId) {
    super(String.format(MESSAGE, airlineId));
    this.airlineId = airlineId;
    this.code = null;
  }

  @Override
  public String getUserName() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    final String parameter = Optional.ofNullable(airlineId)
                                     .map(Object::toString)
                                     .orElse(code);
    return new String[]{ parameter };
  }
}
