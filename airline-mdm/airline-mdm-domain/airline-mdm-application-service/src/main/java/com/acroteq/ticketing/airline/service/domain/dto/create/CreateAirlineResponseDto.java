package com.acroteq.ticketing.airline.service.domain.dto.create;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateAirlineResponseDto {

  @NotNull
  private final Long airlineId;
  @NotNull
  private final String message;
}
