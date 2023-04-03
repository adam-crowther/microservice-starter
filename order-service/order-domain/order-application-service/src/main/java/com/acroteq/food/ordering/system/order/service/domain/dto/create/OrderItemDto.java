package com.acroteq.food.ordering.system.order.service.domain.dto.create;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class OrderItemDto {

  @NotNull private final UUID productId;
  @NotNull private final Integer quantity;
}
