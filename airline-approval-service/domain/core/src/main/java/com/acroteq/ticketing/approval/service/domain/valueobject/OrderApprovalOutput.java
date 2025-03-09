package com.acroteq.ticketing.approval.service.domain.valueobject;

import com.acroteq.ticketing.approval.service.domain.entity.order.OrderApproval;
import com.acroteq.ticketing.domain.validation.ValidationResult;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class OrderApprovalOutput {

  ValidationResult validationResult;
  OrderApproval orderApproval;
}
