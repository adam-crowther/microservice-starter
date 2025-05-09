package com.acroteq.ticketing.payment.service.domain.exception;

import com.acroteq.domain.exception.NotFoundException;
import com.acroteq.domain.valueobject.OrderId;

public class PaymentNotFoundException extends NotFoundException {

  private static final String I18N_CODE = "problem.payment.not.found.for.order.if";
  private static final String MESSAGE = "Payment not found for order id: %s";

  private final String i18nCode;
  private final String parameter;

  public PaymentNotFoundException(final OrderId orderId) {
    super(String.format(MESSAGE, orderId));
    this.parameter = orderId.toString();
    this.i18nCode = I18N_CODE;
  }

  @Override
  public String getUserName() {
    return i18nCode;
  }

  @Override
  public String[] getParameters() {
    return new String[]{ parameter };
  }
}
