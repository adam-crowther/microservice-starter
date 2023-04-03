package com.acroteq.food.ordering.system.restaurant.service.domain.exception;

import com.acroteq.food.ordering.system.domain.exception.DomainException;
import com.acroteq.food.ordering.system.restaurant.service.domain.valueobject.OrderApprovalId;

public class OrderApprovalSaveFailedException extends DomainException {

  private static final String I18N_CODE = "problem.order.approval.save.failed";
  private static final String MESSAGE = "Failed while saving order approval: %s";

  private final OrderApprovalId orderApprovalId;

  public OrderApprovalSaveFailedException(final OrderApprovalId orderApprovalId) {
    super(String.format(MESSAGE, orderApprovalId));
    this.orderApprovalId = orderApprovalId;
  }

  @Override
  public String getCode() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    return new String[]{ orderApprovalId.toString() };
  }
}
