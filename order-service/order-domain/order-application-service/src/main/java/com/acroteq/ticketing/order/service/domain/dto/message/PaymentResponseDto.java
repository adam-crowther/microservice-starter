package com.acroteq.ticketing.order.service.domain.dto.message;

import static com.acroteq.ticketing.domain.validation.ValidationResult.pass;

import com.acroteq.ticketing.domain.validation.ValidationResult;
import com.acroteq.ticketing.domain.valueobject.CashValue;
import com.acroteq.ticketing.domain.valueobject.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
public class PaymentResponseDto {

  @NotNull
  private final UUID sagaId;
  @NotNull
  private final Long orderId;
  @NotNull
  private final Long paymentId;
  @NotNull
  private final Long customerId;
  @NotNull
  private final CashValue value;
  @NotNull
  private final Instant createdDateTime;
  @NotNull
  private final PaymentStatus paymentStatus;
  @Builder.Default
  @NotNull
  private final ValidationResult result = pass();
}
