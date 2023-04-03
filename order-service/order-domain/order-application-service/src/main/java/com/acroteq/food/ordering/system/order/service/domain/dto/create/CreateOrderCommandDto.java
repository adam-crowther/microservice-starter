package com.acroteq.food.ordering.system.order.service.domain.dto.create;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
public class CreateOrderCommandDto {

  @NotNull private final UUID customerId;
  @NotNull private final UUID restaurantId;
  @NotNull private final List<OrderItemDto> items;
  @NotNull private final OrderAddressDto address;
}
