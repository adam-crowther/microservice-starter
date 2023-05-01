package com.acroteq.ticketing.order.service.domain.dto.create;

import com.acroteq.ticketing.application.dto.Dto;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderItemDto implements Dto {

  @NotNull
  private final Long flightId;
  @NotNull
  private final Integer quantity;
}
