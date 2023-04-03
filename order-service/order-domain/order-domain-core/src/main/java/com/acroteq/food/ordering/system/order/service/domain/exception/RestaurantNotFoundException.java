package com.acroteq.food.ordering.system.order.service.domain.exception;

import com.acroteq.food.ordering.system.domain.exception.DomainException;
import com.acroteq.food.ordering.system.domain.valueobject.RestaurantId;

public class RestaurantNotFoundException extends DomainException {

  private static final String MESSAGE = "Customer not found %s";

  public RestaurantNotFoundException(final RestaurantId restaurantId) {
    super(String.format(MESSAGE, restaurantId));
  }
}
