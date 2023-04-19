package com.acroteq.ticketing.airline.service.domain.valueobject;

import com.acroteq.ticketing.airline.service.domain.entity.OrderApproval;
import com.acroteq.ticketing.domain.validation.ValidationResult;

public record OrderApprovalOutput(ValidationResult validationResult, OrderApproval orderApproval) {}
