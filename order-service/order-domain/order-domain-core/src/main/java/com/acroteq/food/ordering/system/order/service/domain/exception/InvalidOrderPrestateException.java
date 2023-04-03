package com.acroteq.food.ordering.system.order.service.domain.exception;

import com.acroteq.food.ordering.system.domain.exception.DomainException;
import com.acroteq.food.ordering.system.domain.valueobject.OrderStatus;

import java.util.Set;

public class InvalidOrderPrestateException extends DomainException {

  private static final String I18N_CODE = "problem.invalid.order.prestate";
  private static final String MESSAGE = "Order state must be in one of states %s for %s action";

  private final Set<OrderStatus> requiredStates;

  private final String action;

  public InvalidOrderPrestateException(final Set<OrderStatus> requiredStates, final String action) {
    super(String.format(MESSAGE, requiredStates, action));

    this.requiredStates = requiredStates;
    this.action = action;
  }

  @Override
  public String getCode() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    return new String[]{ requiredStates.toString(), action };
  }
}
