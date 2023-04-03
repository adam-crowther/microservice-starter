package com.acroteq.food.ordering.system.order.service.domain.valueobject;

import com.acroteq.food.ordering.system.domain.valueobject.BaseId;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

import static java.util.UUID.randomUUID;

@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrackingId extends BaseId<UUID> {

  public static TrackingId of(final UUID id) {
    return TrackingId.builder()
                     .id(id)
                     .build();
  }

  public static TrackingId random() {
    return TrackingId.of(randomUUID());
  }
}
