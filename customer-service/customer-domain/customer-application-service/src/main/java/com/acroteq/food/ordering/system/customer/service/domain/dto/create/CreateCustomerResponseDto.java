package com.acroteq.food.ordering.system.customer.service.domain.dto.create;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class CreateCustomerResponseDto {

  @NotNull
  private final UUID customerId;
  @NotNull
  private final String message;
}
