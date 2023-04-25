package com.acroteq.ticketing.domain.valueobject;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@EqualsAndHashCode
@ToString
@Getter
@Builder
public class CurrencyId implements Serializable {

  public static final CurrencyId NONE = CurrencyId.of("NONE");

  private final String value;

  public static CurrencyId of(final String id) {
    return CurrencyId.builder()
                     .value(id)
                     .build();
  }

  public boolean isNotNone() {
    return !this.equals(NONE);
  }
}
