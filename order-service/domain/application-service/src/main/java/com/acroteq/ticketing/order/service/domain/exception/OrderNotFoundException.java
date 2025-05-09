package com.acroteq.ticketing.order.service.domain.exception;

import com.acroteq.domain.exception.NotFoundException;
import com.acroteq.domain.valueobject.OrderId;
import com.acroteq.ticketing.order.service.domain.valueobject.TrackingId;

public class OrderNotFoundException extends NotFoundException {

  private static final String I18N_CODE_TRACKING_ID = "problem.order.not.found.for.tracking.id";
  private static final String I18N_CODE_ORDER_ID = "problem.order.not.found.for.order.id";

  private static final String MESSAGE_TRACKING_ID = "Order not found for tracking ID %s";
  private static final String MESSAGE_ORDER_ID = "Order not found for order ID %s";

  private final String i18nCode;
  private final String parameter;

  public OrderNotFoundException(final OrderId orderId) {
    super(String.format(MESSAGE_ORDER_ID, orderId));
    this.i18nCode = I18N_CODE_ORDER_ID;
    this.parameter = orderId.toString();
  }

  public OrderNotFoundException(final TrackingId trackingId) {
    super(String.format(MESSAGE_TRACKING_ID, trackingId));
    this.i18nCode = I18N_CODE_TRACKING_ID;
    this.parameter = trackingId.toString();
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
