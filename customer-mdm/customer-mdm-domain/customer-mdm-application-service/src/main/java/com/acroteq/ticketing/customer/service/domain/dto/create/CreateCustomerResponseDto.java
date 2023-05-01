package com.acroteq.ticketing.customer.service.domain.dto.create;

import com.acroteq.ticketing.application.dto.Dto;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateCustomerResponseDto implements Dto {

  @NotNull
  private final Long customerId;
  @NotNull
  private final String message;
}
