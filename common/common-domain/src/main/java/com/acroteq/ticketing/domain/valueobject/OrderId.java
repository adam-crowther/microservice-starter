package com.acroteq.ticketing.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class OrderId extends EntityId {

  public static OrderId of(final Long id) {
    return OrderId.builder()
                  .value(id)
                  .build();
  }
}
