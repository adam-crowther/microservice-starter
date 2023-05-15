package com.acroteq.ticketing.payment.service.domain.valueobject;

import com.acroteq.ticketing.domain.valueobject.EntityId;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class CreditHistoryId extends EntityId {

  public static CreditHistoryId of(final Long id) {
    return CreditHistoryId.builder()
                          .value(id)
                          .build();
  }
}
