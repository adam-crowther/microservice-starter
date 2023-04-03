package com.acroteq.food.ordering.system.payment.service.domain.exception;

import com.acroteq.food.ordering.system.domain.exception.DomainException;

import java.util.UUID;

public class PaymentNotFoundException extends DomainException {

  private static final String I18N_CODE = "problem.payment.not.found";
  private static final String MESSAGE = "Payment not found for order id: ";

  private final String orderId;

  public PaymentNotFoundException(final UUID orderId) {
    super(MESSAGE + orderId);
    this.orderId = orderId.toString();
  }

  @Override
  public String getCode() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    return new String[]{ orderId };
  }
}
