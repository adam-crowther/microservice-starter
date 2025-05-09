package com.acroteq.ticketing.airline.service.domain.exception;

import com.acroteq.domain.exception.DomainException;
import com.acroteq.domain.valueobject.AirlineId;
import com.acroteq.domain.valueobject.FlightId;

import java.util.Objects;
import java.util.Optional;

public class FlightNotInAirlineException extends DomainException {


  private static final String I18N_CODE = "problem.flight.not.in.airline";
  private static final String MESSAGE = "Flight %s is not in airline %s";

  private final AirlineId airlineId;
  private final String airlineCode;
  private final FlightId flightId;
  private final String flightCode;

  public FlightNotInAirlineException(final FlightId flightId, final AirlineId airlineId) {
    super(String.format(MESSAGE, flightId, airlineId));
    this.flightId = flightId;
    this.flightCode = null;
    this.airlineId = airlineId;
    this.airlineCode = null;
  }

  public FlightNotInAirlineException(final String flightCode, final String airlineCode) {
    super(String.format(MESSAGE, flightCode, airlineCode));
    this.flightId = null;
    this.flightCode = flightCode;
    this.airlineId = null;
    this.airlineCode = airlineCode;
  }

  @Override
  public String getUserName() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {

    final String flight = Optional.ofNullable(this.flightId)
                                  .map(FlightId::getValue)
                                  .map(Objects::toString)
                                  .orElse(this.flightCode);

    final String airline = Optional.ofNullable(this.airlineId)
                                   .map(AirlineId::getValue)
                                   .map(Objects::toString)
                                   .orElse(this.airlineCode);

    return new String[]{ flight, airline };
  }


}
