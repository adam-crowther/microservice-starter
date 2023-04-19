package com.acroteq.ticketing.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class CurrencyId extends BaseId {

  public static CurrencyId of(final Long id) {
    return CurrencyId.builder()
                     .value(id)
                     .build();
  }
}
