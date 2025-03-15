package com.acroteq.ticketing.approval.service.domain.valueobject;

import com.acroteq.domain.validation.ValidationResult;
import com.acroteq.ticketing.approval.service.domain.entity.order.OrderApproval;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class OrderApprovalOutput {

  ValidationResult validationResult;
  OrderApproval orderApproval;
}
