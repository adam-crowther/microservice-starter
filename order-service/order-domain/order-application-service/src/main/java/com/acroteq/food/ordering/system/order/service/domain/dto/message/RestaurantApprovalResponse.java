package com.acroteq.food.ordering.system.order.service.domain.dto.message;

import com.acroteq.food.ordering.system.order.service.domain.dto.common.OrderApprovalStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
public class RestaurantApprovalResponse {

  @NotNull private final UUID id;
  @NotNull private final UUID sagaId;
  @NotNull private final String orderId;
  @NotNull private final String restaurantId;
  @NotNull private final Instant createdAt;
  @NotNull private final OrderApprovalStatus orderApprovalStatus;
  @Nullable private final List<String> failureMessages;
}
