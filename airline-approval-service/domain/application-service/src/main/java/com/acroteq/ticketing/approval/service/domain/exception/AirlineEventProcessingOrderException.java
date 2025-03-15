package com.acroteq.ticketing.approval.service.domain.exception;

import com.acroteq.domain.exception.DomainException;
import com.acroteq.domain.valueobject.AirlineId;

public class AirlineEventProcessingOrderException extends DomainException {

  private static final String I18N_CODE = "problem.airline.event.processing.order.error";
  private static final String MESSAGE = "Airline event processing order violated for airline %s";

  private final AirlineId airlineId;

  public AirlineEventProcessingOrderException(final AirlineId airlineId) {
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
