package com.acroteq.food.ordering.system.order.service.domain.precondition;

import com.acroteq.food.ordering.system.order.service.domain.exception.OrderDomainException;
import com.acroteq.food.ordering.system.precondition.Precondition;

import javax.annotation.CheckForNull;

public final class OrderDomainPrecondition {

  private OrderDomainPrecondition() {
  }

  public static void checkPrecondition(
      final boolean expression, @CheckForNull final Object errorMessage) {
    Precondition.checkPrecondition(expression, errorMessage, OrderDomainException::new);
  }

  public static void checkPrecondition(
      final boolean expression, @CheckForNull final Object errorMessage, final Object... parameters) {
    Precondition.checkPrecondition(expression, errorMessage, OrderDomainException::new, parameters);
  }
}
