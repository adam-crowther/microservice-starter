package com.acroteq.ticketing.order.service.domain.dto.create;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderItemDto {

  @NotNull
  private final Long flightId;
  @NotNull
  private final Integer quantity;
}
