package com.acroteq.food.ordering.system.payment.service.domain.valueobject;

import com.acroteq.food.ordering.system.domain.valueobject.BaseId;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

import static java.util.UUID.randomUUID;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class CreditHistoryId extends BaseId<UUID> {

  static CreditHistoryId of(final UUID id) {
    return CreditHistoryId.builder()
                          .value(id)
                          .build();
  }

  public static CreditHistoryId random() {
    return CreditHistoryId.of(randomUUID());
  }
}
