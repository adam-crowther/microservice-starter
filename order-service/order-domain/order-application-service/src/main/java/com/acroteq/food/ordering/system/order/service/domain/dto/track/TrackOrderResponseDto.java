package com.acroteq.food.ordering.system.order.service.domain.dto.track;

import com.acroteq.food.ordering.system.domain.validation.ValidationResult;
import com.acroteq.food.ordering.system.order.service.domain.dto.common.OrderStatus;
import com.acroteq.food.ordering.system.order.service.domain.dto.create.OrderAddressDto;
import com.acroteq.food.ordering.system.order.service.domain.dto.create.OrderItemDto;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.acroteq.food.ordering.system.domain.validation.ValidationResult.pass;

@Builder
@Getter
public class TrackOrderResponseDto {

  @NotNull
  private final UUID customerId;
  @NotNull
  private final UUID restaurantId;
  @NotNull
  private final OrderAddressDto streetAddress;
  @NotNull
  private final List<OrderItemDto> items = new ArrayList<>();
  @NotNull
  private UUID trackingId;
  @NotNull
  private OrderStatus orderStatus;

  @Builder.Default
  @NotNull
  private final ValidationResult result = pass();
}
