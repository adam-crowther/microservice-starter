package com.acroteq.ticketing.customer.service.domain.exception;

import com.acroteq.domain.exception.DomainException;
import com.acroteq.domain.valueobject.CashValue;

public class CustomerCreditLimitException extends DomainException {

  private static final String I18N_CODE = "problem.customer.credit.limit";
  private static final String MESSAGE = "Customer credit limit %s must be greater than zero";

  private final CashValue cashValue;

  public CustomerCreditLimitException(final CashValue cashValue) {
    super(String.format(MESSAGE, cashValue));
    this.cashValue = cashValue;
  }

  @Override
  public String getCode() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    return new String[]{ cashValue.toString() };
  }
}
