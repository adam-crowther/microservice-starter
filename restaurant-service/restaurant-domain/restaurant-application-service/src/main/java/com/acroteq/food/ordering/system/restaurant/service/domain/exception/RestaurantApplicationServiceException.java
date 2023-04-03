package com.acroteq.food.ordering.system.restaurant.service.domain.exception;

import com.acroteq.food.ordering.system.domain.exception.DomainException;

public class RestaurantApplicationServiceException extends DomainException {

  private static final String I18N_CODE = "problem.restaurant.application.error";
  private static final String MESSAGE = "Restaurant Application Error";

  public RestaurantApplicationServiceException() {
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
