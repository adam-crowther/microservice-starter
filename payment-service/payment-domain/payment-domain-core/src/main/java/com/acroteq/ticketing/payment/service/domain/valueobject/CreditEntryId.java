package com.acroteq.ticketing.payment.service.domain.valueobject;

import com.acroteq.ticketing.domain.valueobject.BaseId;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class CreditEntryId extends BaseId {

  public static CreditEntryId of(final Long id) {
    return CreditEntryId.builder()
                        .value(id)
                        .build();
  }
}
