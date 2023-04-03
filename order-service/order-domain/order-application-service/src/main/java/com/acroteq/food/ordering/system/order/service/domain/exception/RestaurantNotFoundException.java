package com.acroteq.food.ordering.system.order.service.domain.exception;

import com.acroteq.food.ordering.system.domain.exception.DomainException;
import com.acroteq.food.ordering.system.domain.valueobject.RestaurantId;

public class RestaurantNotFoundException extends DomainException {

  private static final String I18N_CODE = "problem.restaurant.not.found";
  private static final String MESSAGE = "Restaurant not found %s";

  private final RestaurantId restaurantId;

  public RestaurantNotFoundException(final RestaurantId restaurantId) {
    super(String.format(MESSAGE, restaurantId));
    this.restaurantId = restaurantId;
  }

  @Override
  public String getCode() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    return new String[]{ restaurantId.toString() };
  }
}
