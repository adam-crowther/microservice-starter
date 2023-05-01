package com.acroteq.ticketing.order.service.domain.dto.message;

import static com.acroteq.ticketing.domain.validation.ValidationResult.pass;

import com.acroteq.ticketing.application.dto.Dto;
import com.acroteq.ticketing.domain.validation.ValidationResult;
import com.acroteq.ticketing.domain.valueobject.CashValue;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
public class PaymentCancelledResponseDto implements Dto {

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
  @Builder.Default
  @NotNull
  private final ValidationResult result = pass();
}
