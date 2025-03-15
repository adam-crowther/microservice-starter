package com.acroteq.ticketing.order.service.domain.exception;

import com.acroteq.domain.exception.DomainException;
import com.acroteq.domain.valueobject.CustomerId;

public class CustomerEventProcessingOrderException extends DomainException {

  private static final String I18N_CODE = "problem.customer.event.processing.order.error";
  private static final String MESSAGE = "Customer event processing order violated for customer %s";

  private final CustomerId customerId;

  public CustomerEventProcessingOrderException(final CustomerId customerId) {
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
