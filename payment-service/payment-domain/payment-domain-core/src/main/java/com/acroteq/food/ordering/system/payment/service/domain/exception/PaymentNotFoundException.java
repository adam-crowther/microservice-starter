package com.acroteq.food.ordering.system.payment.service.domain.exception;

import com.acroteq.food.ordering.system.domain.exception.DomainException;
import com.acroteq.food.ordering.system.domain.valueobject.ProductId;

public class PaymentNotFoundException extends DomainException {


  private static final String I18N_CODE = "problem.payment.not.found";
  private static final String MESSAGE = "Payment not found: ";

  private final ProductId paymentId;

  public PaymentNotFoundException(final ProductId paymentId) {
    super(MESSAGE + paymentId);
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
