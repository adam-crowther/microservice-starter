package com.acroteq.food.ordering.system.order.service.data.access.restaurant.exception;

import com.acroteq.food.ordering.system.domain.exception.DomainException;

public class RestaurantDataAccessException extends DomainException {

  private static final String I18N_CODE = "problem.restaurant.data.access.exception";
  private static final String MESSAGE = "No restaurant found";

  public RestaurantDataAccessException() {
    super(MESSAGE);
  }

  @Override
  public String getCode() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    return new String[0];
  }
}
