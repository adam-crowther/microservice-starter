package com.acroteq.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class CustomerId extends EntityId {

  public static CustomerId of(final Long id) {
    return CustomerId.builder()
                     .value(id)
                     .build();
  }
}
