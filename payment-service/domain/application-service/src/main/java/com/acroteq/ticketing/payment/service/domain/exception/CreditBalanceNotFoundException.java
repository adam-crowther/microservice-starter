package com.acroteq.ticketing.payment.service.domain.exception;

import com.acroteq.domain.exception.NotFoundException;
import com.acroteq.domain.valueobject.CustomerId;

import java.util.function.Supplier;

public class CreditBalanceNotFoundException extends NotFoundException {

  private static final String I18N_CODE = "problem.credit.balance.not.found.for.customer.id";
  private static final String MESSAGE = "Credit Balance not found for customer id: %s";

  private final String i18nCode;
  private final CustomerId customerId;

  public static Supplier<CreditBalanceNotFoundException> creditBalanceNotFoundException(final CustomerId customerId) {
    return () -> new CreditBalanceNotFoundException(customerId);
  }

  public CreditBalanceNotFoundException(final CustomerId customerId) {
    super(String.format(MESSAGE, customerId));
    this.i18nCode = I18N_CODE;
    this.customerId = customerId;
  }

  @Override
  public String getUserName() {
    return i18nCode;
  }

  @Override
  public String[] getParameters() {
    return new String[]{ customerId.toString() };
  }
}
