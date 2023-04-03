package com.acroteq.food.ordering.system.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
public abstract class BaseId<T> implements Serializable {

  private final T value;

  @Override
  public String toString() {
    return value.toString();
  }
}
