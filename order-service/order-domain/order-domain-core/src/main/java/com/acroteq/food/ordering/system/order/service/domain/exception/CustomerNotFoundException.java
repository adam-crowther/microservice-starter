package com.acroteq.food.ordering.system.order.service.domain.exception;

import com.acroteq.food.ordering.system.domain.exception.DomainException;
import com.acroteq.food.ordering.system.domain.valueobject.CustomerId;
import com.acroteq.food.ordering.system.domain.valueobject.ProductId;

import java.util.UUID;

public class CustomerNotFoundException extends DomainException {

  private static final String MESSAGE = "Customer not found %s";

  public CustomerNotFoundException(final CustomerId customerId) {
    super(String.format(MESSAGE, customerId));
  }
}
