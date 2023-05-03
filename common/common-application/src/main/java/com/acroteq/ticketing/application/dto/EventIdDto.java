package com.acroteq.ticketing.application.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EventIdDto {

  @NonNull
  private final Long offset;
  @NonNull
  private final Integer partition;
}
