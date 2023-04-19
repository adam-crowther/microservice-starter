package com.acroteq.ticketing.payment.service.domain.exception;

import com.acroteq.ticketing.domain.exception.DomainException;
import com.acroteq.ticketing.payment.service.domain.valueobject.CreditEntryId;

public class CreditEntrySaveFailedException extends DomainException {

  private static final String I18N_CODE = "problem.creditEntry.save.failed";
  private static final String MESSAGE = "Failed while saving credit entry: %s";

  private final CreditEntryId creditEntryId;

  public CreditEntrySaveFailedException(final CreditEntryId creditEntryId) {
    super(String.format(MESSAGE, creditEntryId));
    this.creditEntryId = creditEntryId;
  }

  @Override
  public String getCode() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    return new String[]{ creditEntryId.toString() };
  }
}
