package com.acroteq.ticketing.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class OrderItemId extends EntityId {

  public static OrderItemId of(final Long id) {
    return OrderItemId.builder()
                      .value(id)
                      .build();
  }
}
