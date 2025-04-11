package com.acroteq.ticketing.airline.service.domain.exception;

import com.acroteq.domain.exception.DomainException;
import com.acroteq.domain.valueobject.AirlineId;
import com.acroteq.domain.valueobject.FlightId;

public class FlightIdNotInAirlineException extends DomainException {


  private static final String I18N_CODE = "problem.flight.not.in.airline";
  private static final String MESSAGE = "Flight with id %s is not in airline with id %s";

  private final AirlineId airlineId;
  private final FlightId flightId;

  public FlightIdNotInAirlineException(final FlightId flightId, final AirlineId airlineId) {
    super(String.format(MESSAGE, flightId, airlineId));
    this.flightId = flightId;
    this.airlineId = airlineId;
  }

  @Override
  public String getCode() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    return new String[]{ flightId.getValue().toString(), airlineId.getValue().toString() };
  }
}
