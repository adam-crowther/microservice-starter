package com.acroteq.food.ordering.system.customer.service.application.exception;

import com.acroteq.food.ordering.system.domain.exception.DomainException;

import java.util.UUID;

public class MismatchedCustomerIdException extends DomainException {

  private static final String I18N_CODE = "problem.mismatched.customer.id";
  private static final String MESSAGE = "Mismatched customer id: %s, %s";

  private final UUID urlParamId;
  private final UUID customerId;

  public MismatchedCustomerIdException(final UUID urlParamId, final UUID customerId) {
    super(String.format(MESSAGE, urlParamId, customerId));
    this.urlParamId = urlParamId;
    this.customerId = customerId;
  }

  @Override
  public String getCode() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    return new String[]{ urlParamId.toString(), customerId.toString() };
  }
}
