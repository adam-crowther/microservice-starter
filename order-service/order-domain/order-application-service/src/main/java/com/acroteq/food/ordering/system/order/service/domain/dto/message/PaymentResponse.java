package com.acroteq.food.ordering.system.order.service.domain.dto.message;

import com.acroteq.food.ordering.system.order.service.domain.dto.common.CashValue;
import com.acroteq.food.ordering.system.order.service.domain.dto.common.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
public class PaymentResponse {

  @NotNull private final UUID id;
  @NotNull private final UUID sagaId;
  @NotNull private final String orderId;
  @NotNull private final String paymentId;
  @NotNull private final String customerId;
  @NotNull private final CashValue price;
  @NotNull private final Instant createdAt;
  @NotNull private final PaymentStatus paymentStatus;
  @Nullable private final List<String> failureMessages;
}
