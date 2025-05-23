package com.acroteq.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class AirlineId extends EntityId {

  public static AirlineId of(final Long id) {
    return AirlineId.builder()
                    .value(id)
                    .build();
  }
}
