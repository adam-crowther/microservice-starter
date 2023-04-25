package com.acroteq.ticketing.payment.service.domain.exception;

import com.acroteq.ticketing.domain.exception.DomainException;
import com.acroteq.ticketing.domain.valueobject.CustomerId;

import java.util.function.Supplier;

public class CreditEntryNotFoundException extends DomainException {

  private static final String I18N_CODE = "problem.credit.entry.not.found.for.customer.id";
  private static final String MESSAGE = "Credit Entry not found for customer id: %s";

  private final String i18nCode;
  private final CustomerId customerId;

  public static Supplier<CreditEntryNotFoundException> newCreditEntryNotFoundException(final CustomerId customerId) {
    return () -> new CreditEntryNotFoundException(customerId);
  }

  public CreditEntryNotFoundException(final CustomerId customerId) {
    super(String.format(MESSAGE, customerId));
    this.i18nCode = I18N_CODE;
    this.customerId = customerId;
  }

  @Override
  public String getCode() {
    return i18nCode;
  }

  @Override
  public String[] getParameters() {
    return new String[]{ customerId.toString() };
  }
}
