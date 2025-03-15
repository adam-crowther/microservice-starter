package com.acroteq.ticketing.order.service.domain.exception;

import com.acroteq.domain.exception.DomainException;
import com.acroteq.domain.valueobject.CustomerId;

public class CustomerNotFoundException extends DomainException {

  private static final String I18N_CODE = "problem.customer.not.found";
  private static final String MESSAGE = "Customer not found %s";

  private final CustomerId customerId;

  public CustomerNotFoundException(final CustomerId customerId) {
    super(String.format(MESSAGE, customerId));
    this.customerId = customerId;
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
