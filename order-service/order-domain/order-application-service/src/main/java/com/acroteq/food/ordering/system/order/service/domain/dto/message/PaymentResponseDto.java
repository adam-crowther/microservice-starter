package com.acroteq.food.ordering.system.order.service.domain.dto.message;

import com.acroteq.food.ordering.system.domain.validation.ValidationResult;
import com.acroteq.food.ordering.system.domain.valueobject.CashValue;
import com.acroteq.food.ordering.system.order.service.domain.dto.common.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

import static com.acroteq.food.ordering.system.domain.validation.ValidationResult.pass;

@Builder
@Getter
public class PaymentResponseDto {

  @NotNull private final UUID id;
  @NotNull private final UUID sagaId;
  @NotNull private final UUID orderId;
  @NotNull private final UUID paymentId;
  @NotNull private final UUID customerId;
  @NotNull private final CashValue value;
  @NotNull private final Instant createdDateTime;
  @NotNull private final PaymentStatus paymentStatus;
  @Builder.Default
  @NotNull
  private final ValidationResult result = pass();
}
