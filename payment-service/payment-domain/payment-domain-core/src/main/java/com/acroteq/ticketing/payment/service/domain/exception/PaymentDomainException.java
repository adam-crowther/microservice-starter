package com.acroteq.ticketing.payment.service.domain.exception;

import com.acroteq.ticketing.domain.exception.DomainException;

public class PaymentDomainException extends DomainException {

  private static final String I18N_CODE = "problem.payment.error";
  private static final String MESSAGE = "Payment error";

  public PaymentDomainException() {
    super(MESSAGE);
  }

  @Override
  public String getCode() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    return new String[0];
  }
}
