package com.acroteq.ticketing.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class PaymentId extends EntityId {

  public static PaymentId of(final Long id) {
    return PaymentId.builder()
                    .value(id)
                    .build();
  }
}
