package com.acroteq.ticketing.payment.service.domain.exception;

import com.acroteq.domain.exception.DomainException;
import com.acroteq.domain.valueobject.PaymentId;

public class PaymentSaveFailedException extends DomainException {

  private static final String I18N_CODE = "problem.payment.save.failed";
  private static final String MESSAGE = "Failed while saving payment: %s";

  private final PaymentId paymentId;

  public PaymentSaveFailedException(final PaymentId paymentId) {
    super(String.format(MESSAGE, paymentId));
    this.paymentId = paymentId;
  }

  @Override
  public String getCode() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    return new String[]{ paymentId.toString() };
  }
}
