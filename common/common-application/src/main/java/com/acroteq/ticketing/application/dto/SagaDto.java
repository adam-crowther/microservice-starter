package com.acroteq.ticketing.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@SuperBuilder
public class SagaDto implements DataTransferObject {

  @NotNull
  private final UUID sagaId;
}
