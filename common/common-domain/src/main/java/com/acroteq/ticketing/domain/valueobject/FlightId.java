package com.acroteq.ticketing.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class FlightId extends EntityId {

  public static FlightId of(final Long id) {
    return FlightId.builder()
                   .value(id)
                   .build();
  }
}
