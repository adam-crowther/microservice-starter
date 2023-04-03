package com.acroteq.food.ordering.system.order.service.domain.exception;

import com.acroteq.food.ordering.system.domain.exception.DomainException;
import com.acroteq.food.ordering.system.domain.valueobject.OrderId;
import com.acroteq.food.ordering.system.order.service.domain.valueobject.TrackingId;

public class OrderNotFoundException extends DomainException {

  private static final String I18N_CODE_TRACKING_ID = "problem.order.not.found.for.tracking.id";
  private static final String I18N_CODE_ORDER_ID = "problem.order.not.found.for.order.id";

  private static final String MESSAGE_TRACKING_ID = "Order not found for tracking ID %s";
  private static final String MESSAGE_ORDER_ID = "Order not found for order ID %s";

  private final OrderId orderId;

  private final TrackingId trackingId;

  public OrderNotFoundException(final OrderId orderId) {
    super(String.format(MESSAGE_ORDER_ID, orderId));
    this.orderId = orderId;
    this.trackingId = null;
  }

  public OrderNotFoundException(final TrackingId trackingId) {
    super(String.format(MESSAGE_TRACKING_ID, trackingId));
    this.orderId = null;
    this.trackingId = trackingId;
  }

  @Override
  public String getCode() {
    if (orderId != null) {
      return I18N_CODE_ORDER_ID;
    } else {
      return I18N_CODE_TRACKING_ID;
    }
  }

  @Override
  public String[] getParameters() {
    if (orderId != null) {
      return new String[]{ orderId.toString() };
    } else {
      return new String[]{ trackingId.toString() };
    }
  }
}
