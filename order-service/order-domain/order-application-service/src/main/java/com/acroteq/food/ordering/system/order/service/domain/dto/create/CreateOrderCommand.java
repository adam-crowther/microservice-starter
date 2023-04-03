package com.acroteq.food.ordering.system.order.service.domain.dto.create;

import com.acroteq.food.ordering.system.order.service.domain.dto.common.CashValue;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
public class CreateOrderCommand {

  @NotNull private final UUID customerId;
  @NotNull private final UUID restaurantId;
  @NotNull private final List<OrderItem> items;
  @NotNull private final OrderAddress address;
}
