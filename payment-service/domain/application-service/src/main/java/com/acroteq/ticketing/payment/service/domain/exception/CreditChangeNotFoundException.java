package com.acroteq.ticketing.payment.service.domain.exception;

import com.acroteq.domain.exception.NotFoundException;
import com.acroteq.domain.valueobject.CustomerId;

import java.util.function.Supplier;

public class CreditChangeNotFoundException extends NotFoundException {

  private static final String I18N_CODE = "problem.credit.change.not.found";
  private static final String MESSAGE = "Credit change not found for customer: %s";

  private final CustomerId customerId;

  public static Supplier<CreditChangeNotFoundException> creditChangeNotFoundException(final CustomerId customerId) {
    return () -> new CreditChangeNotFoundException(customerId);
  }

  public CreditChangeNotFoundException(final CustomerId customerId) {
    super(String.format(MESSAGE, customerId));
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
