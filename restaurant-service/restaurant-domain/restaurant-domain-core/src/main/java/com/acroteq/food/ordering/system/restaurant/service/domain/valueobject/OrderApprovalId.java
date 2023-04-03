package com.acroteq.food.ordering.system.restaurant.service.domain.valueobject;

import com.acroteq.food.ordering.system.domain.valueobject.BaseId;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

import static java.util.UUID.randomUUID;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class OrderApprovalId extends BaseId<UUID> {

  static OrderApprovalId of(final UUID id) {
    return OrderApprovalId.builder()
                    .value(id)
                    .build();
  }

  public static OrderApprovalId random() {
    return OrderApprovalId.of(randomUUID());
  }
}
