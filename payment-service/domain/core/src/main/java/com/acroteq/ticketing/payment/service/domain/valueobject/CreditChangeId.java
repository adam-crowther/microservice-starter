package com.acroteq.ticketing.payment.service.domain.valueobject;

import com.acroteq.domain.valueobject.EntityId;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class CreditChangeId extends EntityId {

  public static CreditChangeId of(final Long id) {
    return CreditChangeId.builder()
                         .value(id)
                         .build();
  }
}
