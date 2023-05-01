package com.acroteq.ticketing.order.service.data.access.customer.exception;

import com.acroteq.ticketing.domain.exception.DomainException;
import com.acroteq.ticketing.domain.valueobject.CustomerId;

public class CustomerAlreadyExistsException extends DomainException {

  private static final String I18N_CODE = "problem.customer.already.exists";
  private static final String MESSAGE = "Customer %s already exists";

  private final CustomerId customerId;

  public CustomerAlreadyExistsException(final CustomerId customerId) {
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
