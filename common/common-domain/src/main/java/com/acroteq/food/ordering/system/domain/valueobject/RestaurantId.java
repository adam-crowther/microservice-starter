package com.acroteq.food.ordering.system.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

import static java.util.UUID.randomUUID;

@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RestaurantId extends BaseId<UUID> {

  public static RestaurantId of(final UUID id) {
    return RestaurantId.builder()
                       .id(id)
                       .build();
  }

  public static RestaurantId random() {
    return RestaurantId.of(randomUUID());
  }
}
