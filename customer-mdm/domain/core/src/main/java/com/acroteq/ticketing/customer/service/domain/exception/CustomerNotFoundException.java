package com.acroteq.ticketing.customer.service.domain.exception;

import com.acroteq.domain.exception.NotFoundException;
import com.acroteq.domain.valueobject.CustomerId;

public class CustomerNotFoundException extends NotFoundException {


  private static final String I18N_CODE = "problem.customer.not.found";
  private static final String MESSAGE = "Customer not found: ";

  private final CustomerId customerId;

  public CustomerNotFoundException(final CustomerId customerId) {
    super(MESSAGE + customerId);
    this.customerId = customerId;
  }

  @Override
  public String getUserName() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    return new String[]{ customerId.toString() };
  }
}
