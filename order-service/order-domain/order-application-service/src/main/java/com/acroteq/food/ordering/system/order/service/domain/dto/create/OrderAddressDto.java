package com.acroteq.food.ordering.system.order.service.domain.dto.create;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderAddressDto {

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
