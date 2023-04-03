package com.acroteq.food.ordering.system.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

import static java.util.UUID.randomUUID;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class RestaurantId extends BaseId<UUID> {

  public static RestaurantId of(final UUID id) {
    return RestaurantId.builder()
                       .value(id)
                       .build();
  }

  public static RestaurantId of(final String id) {
    return of(UUID.fromString(id));
  }

  public static RestaurantId random() {
    return RestaurantId.of(randomUUID());
  }
}
