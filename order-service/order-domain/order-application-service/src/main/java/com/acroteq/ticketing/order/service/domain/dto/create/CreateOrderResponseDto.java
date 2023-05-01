package com.acroteq.ticketing.order.service.domain.dto.create;

import com.acroteq.ticketing.application.dto.Dto;
import com.acroteq.ticketing.domain.valueobject.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class CreateOrderResponseDto implements Dto {

  @NotNull
  private final UUID trackingId;
  @NotNull
  private final OrderStatus status;
  @NotNull
  private final String message;
}
