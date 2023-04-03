package com.acroteq.food.ordering.system.order.service.domain.dto.create;

import com.acroteq.food.ordering.system.order.service.domain.dto.common.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class CreateOrderResponseDto {

  @NotNull private final UUID trackingId;
  @NotNull private final OrderStatus status;
  @NotNull private final String message;
}
