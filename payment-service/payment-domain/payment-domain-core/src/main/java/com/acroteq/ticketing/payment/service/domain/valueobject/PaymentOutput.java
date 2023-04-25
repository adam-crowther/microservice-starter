package com.acroteq.ticketing.payment.service.domain.valueobject;

import com.acroteq.ticketing.domain.validation.ValidationResult;
import com.acroteq.ticketing.payment.service.domain.entity.CreditEntry;
import com.acroteq.ticketing.payment.service.domain.entity.CreditHistory;
import com.acroteq.ticketing.payment.service.domain.entity.Payment;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class PaymentOutput {

  Payment payment;
  CreditEntry creditEntry;
  CreditHistory creditHistory;
  ValidationResult validationResult;
}
