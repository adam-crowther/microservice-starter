package com.acroteq.ticketing.payment.service.domain.exception;

import com.acroteq.domain.exception.DomainException;
import com.acroteq.domain.valueobject.PaymentId;

public class MissingCreditChangeException extends DomainException {

  private static final String I18N_CODE = "problem.missing.credit.change";
  private static final String MESSAGE = "If the payment passed, then a credit change is required: %s";

  private final PaymentId paymentId;

  public MissingCreditChangeException(final PaymentId paymentId) {
    super(String.format(MESSAGE, paymentId));
    this.paymentId = paymentId;
  }

  @Override
  public String getUserName() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    return new String[]{ paymentId.toString() };
  }
}
