package com.acroteq.ticketing.payment.service.domain.exception;

import com.acroteq.ticketing.domain.exception.DomainException;
import com.acroteq.ticketing.domain.valueobject.CustomerId;

import java.util.function.Supplier;

public class CreditHistoryNotFoundException extends DomainException {

  private static final String I18N_CODE = "problem.payment.application.error";
  private static final String MESSAGE = "Credit history not found for customer: ";

  private final CustomerId customerId;

  public static Supplier<CreditHistoryNotFoundException> creditHistoryNotFoundException(final CustomerId customerId) {
    return () -> new CreditHistoryNotFoundException(customerId);
  }

  public CreditHistoryNotFoundException(final CustomerId customerId) {
    super(MESSAGE + customerId);
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
