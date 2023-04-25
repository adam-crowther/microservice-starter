package com.acroteq.ticketing.customer.service.domain.exception;

import com.acroteq.ticketing.domain.exception.DomainException;

public class CustomerSaveFailedException extends DomainException {

  private static final String I18N_CODE = "problem.customer.save.failed";
  private static final String MESSAGE_WITHOUT_ID = "Failed while saving customer";

  public CustomerSaveFailedException() {
    super(MESSAGE_WITHOUT_ID);
  }

  @Override
  public String getCode() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    return new String[0];
  }
}
