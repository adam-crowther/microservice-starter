package com.acroteq.food.ordering.system.restaurant.service.data.access.restaurant.exception;

import com.acroteq.food.ordering.system.domain.exception.DomainException;

public class NoRestaurantsFoundException extends DomainException {

  private static final String I18N_CODE = "problem.no.restaurants.found";
  private static final String MESSAGE = "No Restaurants Found";

  public NoRestaurantsFoundException() {
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
