package com.acroteq.food.ordering.system.customer.service.domain.exception;

import com.acroteq.food.ordering.system.domain.exception.DomainException;
import com.acroteq.food.ordering.system.domain.valueobject.CustomerId;

import java.util.UUID;

public class CustomerNotFoundException extends DomainException {


  private static final String I18N_CODE = "problem.customer.not.found";
  private static final String MESSAGE = "Customer not found: ";

  private final CustomerId customerId;

  public CustomerNotFoundException(final CustomerId customerId) {
    super(MESSAGE + customerId);
    this.customerId = customerId;
  }

  public CustomerNotFoundException(final UUID customerId) {
    super(MESSAGE + customerId);
    this.customerId = CustomerId.of(customerId);
  }

  @Override
  public String getCode() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    return new String[]{ customerId.toString() };
  }
}
