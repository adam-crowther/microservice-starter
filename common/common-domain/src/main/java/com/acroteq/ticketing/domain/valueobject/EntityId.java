package com.acroteq.ticketing.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
public abstract class EntityId implements Serializable {

  @NonNull
  private final Long value;

  @Override
  public String toString() {
    return value.toString();
  }
}
