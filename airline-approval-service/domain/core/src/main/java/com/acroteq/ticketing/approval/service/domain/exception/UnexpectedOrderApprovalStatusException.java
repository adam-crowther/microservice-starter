package com.acroteq.ticketing.approval.service.domain.exception;

import com.acroteq.ticketing.domain.exception.DomainException;
import com.acroteq.ticketing.domain.valueobject.OrderApprovalStatus;

public class UnexpectedOrderApprovalStatusException extends DomainException {

  private static final String I18N_CODE = "problem.unexpected.order.approval.status";
  private static final String MESSAGE = "Unexpected order approval status: %s";

  private final OrderApprovalStatus orderApprovalStatus;

  public UnexpectedOrderApprovalStatusException(final OrderApprovalStatus orderApprovalStatus) {
    super(String.format(MESSAGE, orderApprovalStatus));

    this.orderApprovalStatus = orderApprovalStatus;
  }

  @Override
  public String getCode() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    return new String[]{ orderApprovalStatus.toString() };
  }
}
