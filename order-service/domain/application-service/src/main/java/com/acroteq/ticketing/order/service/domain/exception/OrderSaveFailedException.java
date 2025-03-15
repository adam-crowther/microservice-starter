package com.acroteq.ticketing.order.service.domain.exception;

import com.acroteq.domain.exception.DomainException;
import com.acroteq.domain.valueobject.OrderId;

public class OrderSaveFailedException extends DomainException {

  private static final String I18N_CODE = "problem.order.save.failed";
  private static final String MESSAGE = "Failed while saving order: %s";

  private final OrderId orderId;

  public OrderSaveFailedException(final OrderId orderId) {
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
