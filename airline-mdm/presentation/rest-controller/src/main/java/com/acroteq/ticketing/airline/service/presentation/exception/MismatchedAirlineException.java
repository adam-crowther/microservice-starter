package com.acroteq.ticketing.airline.service.presentation.exception;

import com.acroteq.domain.exception.DomainException;

public class MismatchedAirlineException extends DomainException {

  private static final String I18N_CODE = "problem.mismatched.airline.code";
  private static final String MESSAGE = "Mismatched airline code: %s, %s";

  private final String urlParamCode;
  private final String airlineCode;

  public MismatchedAirlineException(final String urlParamCode, final String airlineCode) {
    super(String.format(MESSAGE, urlParamCode, airlineCode));
    this.urlParamCode = urlParamCode;
    this.airlineCode = airlineCode;
  }

  @Override
  public String getCode() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    return new String[]{ urlParamCode, airlineCode };
  }
}
