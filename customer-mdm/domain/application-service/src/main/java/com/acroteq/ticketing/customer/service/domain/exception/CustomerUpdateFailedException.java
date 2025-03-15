package com.acroteq.ticketing.customer.service.domain.exception;

import com.acroteq.domain.exception.DomainException;
import com.acroteq.domain.valueobject.CustomerId;

public class CustomerUpdateFailedException extends DomainException {

  private static final String I18N_CODE = "problem.customer.update.failed";
  private static final String MESSAGE = "Failed while updating customer: %s";

  private final CustomerId customerId;

  public CustomerUpdateFailedException(final CustomerId customerId) {
    super(String.format(MESSAGE, customerId));
    this.customerId = customerId;
  }

  @Override
  public String getCode() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    return new String[]{ customerId.getValue().toString() };
  }
}
