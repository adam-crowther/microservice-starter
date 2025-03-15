package com.acroteq.ticketing.airline.service.presentation.exception;

import com.acroteq.domain.exception.DomainException;

public class MismatchedAirlineIdException extends DomainException {

  private static final String I18N_CODE = "problem.mismatched.airline.id";
  private static final String MESSAGE = "Mismatched airline id: %s, %s";

  private final Long urlParamId;
  private final Long airlineId;

  public MismatchedAirlineIdException(final Long urlParamId, final Long airlineId) {
    super(String.format(MESSAGE, urlParamId, airlineId));
    this.urlParamId = urlParamId;
    this.airlineId = airlineId;
  }

  @Override
  public String getCode() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    return new String[]{ urlParamId.toString(), airlineId.toString() };
  }
}
