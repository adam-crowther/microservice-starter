package com.acroteq.ticketing.payment.service.domain.valueobject;

import com.acroteq.domain.valueobject.EntityId;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class CreditBalanceId extends EntityId {

  public static CreditBalanceId of(final Long id) {
    return CreditBalanceId.builder()
                          .value(id)
                          .build();
  }
}
