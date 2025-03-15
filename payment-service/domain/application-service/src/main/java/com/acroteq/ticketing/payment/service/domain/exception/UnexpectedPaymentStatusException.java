package com.acroteq.ticketing.payment.service.domain.exception;

import com.acroteq.domain.exception.DomainException;
import com.acroteq.domain.valueobject.PaymentStatus;

public class UnexpectedPaymentStatusException extends DomainException {

  private static final String I18N_CODE = "problem.unexpected.payment.status";
  private static final String MESSAGE = "Unexpected payment status: %s";

  private final PaymentStatus paymentStatus;

  public UnexpectedPaymentStatusException(final PaymentStatus paymentStatus) {
    super(String.format(MESSAGE, paymentStatus));

    this.paymentStatus = paymentStatus;
  }

  @Override
  public String getCode() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    return new String[]{ paymentStatus.toString() };
  }
}
