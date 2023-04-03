package com.acroteq.food.ordering.system.order.service.domain.dto.track;

import com.acroteq.food.ordering.system.order.service.domain.dto.common.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
public class TrackOrderResponse {

  @NotNull private final UUID trackingId;
  @NotNull private final OrderStatus orderStatus;
  @Nullable private final List<String> failureMessages;
}
