package com.acroteq.domain.valueobject;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.io.Serializable;

@EqualsAndHashCode
@ToString(includeFieldNames = false)
@Getter
@Builder
public class CurrencyId implements Serializable {

  public static final CurrencyId NONE = of("NONE");

  @NonNull
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
