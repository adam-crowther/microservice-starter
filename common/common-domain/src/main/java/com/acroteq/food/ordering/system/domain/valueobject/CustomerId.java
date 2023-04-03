package com.acroteq.food.ordering.system.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

import static java.util.UUID.randomUUID;

@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CustomerId extends BaseId<UUID> {

  public static CustomerId of(final UUID id) {
    return CustomerId.builder()
                     .id(id)
                     .build();
  }

  public static CustomerId random() {
    return CustomerId.of(randomUUID());
  }
}
