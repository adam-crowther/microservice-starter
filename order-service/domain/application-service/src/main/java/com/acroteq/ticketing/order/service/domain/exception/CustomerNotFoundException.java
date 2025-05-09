package com.acroteq.ticketing.order.service.domain.exception;

import com.acroteq.domain.exception.NotFoundException;
import com.acroteq.domain.valueobject.CustomerId;

import java.util.Optional;

public class CustomerNotFoundException extends NotFoundException {

  private static final String I18N_CODE = "problem.customer.not.found";
  private static final String MESSAGE = "Customer not found %s";

  private final CustomerId customerId;
  private final String userName;

  public CustomerNotFoundException(final String userName) {
    super(String.format(MESSAGE, userName));
    this.customerId = null;
    this.userName = userName;
  }

  public CustomerNotFoundException(final CustomerId customerId) {
    super(String.format(MESSAGE, customerId));
    this.customerId = customerId;
    this.userName = null;
  }

  @Override
  public String getUserName() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    final String parameter = Optional.ofNullable(customerId)
                                     .map(Object::toString)
                                     .orElse(userName);
    return new String[]{ parameter };
  }
}
