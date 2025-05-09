package com.acroteq.ticketing.customer.service.presentation.exception;

import com.acroteq.domain.exception.DomainException;

public class MismatchedCustomerIdException extends DomainException {

  private static final String I18N_CODE = "problem.mismatched.customer.id";
  private static final String MESSAGE = "Mismatched customer id: %s, %s";

  private final Long urlParamId;
  private final Long customerId;

  public MismatchedCustomerIdException(final Long urlParamId, final Long customerId) {
    super(String.format(MESSAGE, urlParamId, customerId));
    this.urlParamId = urlParamId;
    this.customerId = customerId;
  }

  @Override
  public String getUserName() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    return new String[]{ urlParamId.toString(), customerId.toString() };
  }
}
