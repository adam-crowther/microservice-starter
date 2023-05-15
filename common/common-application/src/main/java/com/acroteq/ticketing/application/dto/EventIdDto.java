package com.acroteq.ticketing.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode
public final class EventIdDto {

  @NotNull
  private final Long offset;
  @NotNull
  private final Integer partition;
}
