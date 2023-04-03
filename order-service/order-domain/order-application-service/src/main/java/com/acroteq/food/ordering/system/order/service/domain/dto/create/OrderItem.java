package com.acroteq.food.ordering.system.order.service.domain.dto.create;

import com.acroteq.food.ordering.system.order.service.domain.dto.common.CashValue;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class OrderItem {

  @NotNull private final UUID productId;
  @NotNull private final Integer quantity;
}
