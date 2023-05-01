package com.acroteq.ticketing.airline.service.domain.dto.create;

import com.acroteq.ticketing.application.dto.Dto;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateAirlineResponseDto implements Dto {

  @NotNull
  private final Long airlineId;
  @NotNull
  private final String message;
}
