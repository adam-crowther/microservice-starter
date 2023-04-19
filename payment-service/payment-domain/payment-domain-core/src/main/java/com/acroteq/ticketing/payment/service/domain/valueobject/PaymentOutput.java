package com.acroteq.ticketing.payment.service.domain.valueobject;

import com.acroteq.ticketing.domain.validation.ValidationResult;
import com.acroteq.ticketing.payment.service.domain.entity.Payment;

public record PaymentOutput(Payment payment, ValidationResult validationResult) {}
