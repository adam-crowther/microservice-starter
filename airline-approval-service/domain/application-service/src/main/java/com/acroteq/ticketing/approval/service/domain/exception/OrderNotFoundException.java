package com.acroteq.ticketing.approval.service.domain.exception;

import com.acroteq.domain.exception.DomainException;
import com.acroteq.domain.valueobject.OrderId;

public class OrderNotFoundException extends DomainException {

  private static final String I18N_CODE = "problem.order.not.found";
  private static final String MESSAGE = "Order not found %s";

  private final OrderId orderId;

  public OrderNotFoundException(final OrderId orderId) {
    super(String.format(MESSAGE, orderId));
    this.orderId = orderId;
  }

  @Override
  public String getCode() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    return new String[]{ orderId.toString() };
  }
}
