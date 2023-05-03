package com.acroteq.ticketing.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class AuditedSagaDto extends SagaDto {

  @NotNull
  private final AuditDto audit;
}
