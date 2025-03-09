package com.acroteq.domain.event;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
public abstract class SagaEvent implements Event {

  @Builder.Default
  @NonNull
  private final UUID sagaId = UUID.randomUUID();
}
