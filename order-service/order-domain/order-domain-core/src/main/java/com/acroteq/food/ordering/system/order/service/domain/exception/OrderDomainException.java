package com.acroteq.food.ordering.system.order.service.domain.exception;

import com.acroteq.food.ordering.system.domain.exception.DomainException;

/**
 * TODO: Typed exceptions!
 */
public class OrderDomainException extends DomainException {

  public static final String MESSAGE = "Order Domain Exception";

  public OrderDomainException(final String message) {
    super(MESSAGE + ": " + message);
  }

  public OrderDomainException(final String message, final Object... parameters) {
    super(MESSAGE + ": " + String.format(message, parameters));
  }

  public OrderDomainException(final String message, final Throwable cause) {
    super(MESSAGE + ": " + message, cause);
  }

  public OrderDomainException(final String message, final Throwable cause, final Object... parameters) {
    super(MESSAGE + ": " + String.format(message, parameters), cause);
  }

  public OrderDomainException() {
    super(MESSAGE);
  }

  public OrderDomainException(final Throwable cause) {
    super(MESSAGE, cause);
  }
}
