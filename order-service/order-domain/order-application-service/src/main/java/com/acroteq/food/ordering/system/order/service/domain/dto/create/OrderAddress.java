package com.acroteq.food.ordering.system.order.service.domain.dto.create;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Getter
public class OrderAddress {

  @NotNull
  @Max(value = 50)
  private final String street;

  @Max(value = 10)
  @NotNull
  private final String postalCode;

  @Max(value = 50)
  @NotNull
  private final String city;
}
