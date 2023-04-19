package com.acroteq.ticketing.customer.service.domain.dto.create;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateCustomerResponseDto {

  @NotNull
  private final Long customerId;
  @NotNull
  private final String message;
}
