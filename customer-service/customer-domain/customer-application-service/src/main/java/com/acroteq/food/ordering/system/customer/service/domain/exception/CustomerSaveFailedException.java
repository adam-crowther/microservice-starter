package com.acroteq.food.ordering.system.customer.service.domain.exception;

import com.acroteq.food.ordering.system.domain.exception.DomainException;
import com.acroteq.food.ordering.system.domain.valueobject.CustomerId;

public class CustomerSaveFailedException extends DomainException {

  private static final String I18N_CODE = "problem.customer.save.failed";
  private static final String MESSAGE = "Failed while saving customer: %s";

  private final String customerId;

  public CustomerSaveFailedException(final CustomerId customerId) {
    super(String.format(MESSAGE, customerId));
    this.customerId = customerId.getValue().toString();
  }

  @Override
  public String getCode() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    return new String[]{ customerId };
  }
}
