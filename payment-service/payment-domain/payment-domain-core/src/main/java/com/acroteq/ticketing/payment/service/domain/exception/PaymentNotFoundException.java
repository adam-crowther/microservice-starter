package com.acroteq.ticketing.payment.service.domain.exception;

import com.acroteq.ticketing.domain.exception.DomainException;
import com.acroteq.ticketing.domain.valueobject.FlightId;
import com.acroteq.ticketing.domain.valueobject.OrderId;

public class PaymentNotFoundException extends DomainException {

  private static final String I18N_CODE_PAYMENT_ID = "problem.payment.not.found.for.payment.id";
  private static final String I18N_CODE_ORDER_ID = "problem.payment.not.found.for.order.if";
  private static final String MESSAGE_PAYMENT_ID = "Payment not found for payment id: %s";
  private static final String MESSAGE_ORDER_ID = "Payment not found for order id: %s";

  private final String i18nCode;
  private final String parameter;

  public PaymentNotFoundException(final FlightId paymentId) {
    super(String.format(MESSAGE_PAYMENT_ID, paymentId));
    this.i18nCode = I18N_CODE_PAYMENT_ID;
    this.parameter = paymentId.toString();
  }

  public PaymentNotFoundException(final OrderId orderId) {
    super(String.format(MESSAGE_ORDER_ID, orderId));
    this.parameter = orderId.toString();
    this.i18nCode = I18N_CODE_ORDER_ID;
  }

  @Override
  public String getCode() {
    return i18nCode;
  }

  @Override
  public String[] getParameters() {
    return new String[]{ parameter };
  }
}
