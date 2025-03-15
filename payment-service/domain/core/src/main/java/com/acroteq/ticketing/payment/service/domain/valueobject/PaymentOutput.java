package com.acroteq.ticketing.payment.service.domain.valueobject;

import com.acroteq.domain.validation.ValidationResult;
import com.acroteq.ticketing.payment.service.domain.entity.CreditBalance;
import com.acroteq.ticketing.payment.service.domain.entity.CreditChange;
import com.acroteq.ticketing.payment.service.domain.entity.Payment;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.Optional;

@Builder
@Value
public final class PaymentOutput {

  @NonNull Payment payment;
  @NonNull CreditBalance originalCreditBalance;
  @NonNull CreditBalance updatedCreditBalance;
  @Nullable
  CreditChange newCreditChange;
  @NonNull ValidationResult validationResult;

  public CreditBalance getCreditBalance() {
    return validationResult.isPass() ? updatedCreditBalance : originalCreditBalance;
  }

  public Optional<CreditChange> getCreditChange() {
    return validationResult.isPass() ? Optional.ofNullable(newCreditChange) : Optional.empty();
  }
}
