package com.acroteq.food.ordering.system.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

import static java.util.UUID.randomUUID;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class CustomerId extends BaseId<UUID> {

  public static CustomerId of(final UUID id) {
    return CustomerId.builder()
                     .value(id)
                     .build();
  }

  public static CustomerId of(final String id) {
    return of(UUID.fromString(id));
  }

  public static CustomerId random() {
    return CustomerId.of(randomUUID());
  }
}
