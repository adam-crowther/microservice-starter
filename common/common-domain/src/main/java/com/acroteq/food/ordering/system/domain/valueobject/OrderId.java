package com.acroteq.food.ordering.system.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

import static java.util.UUID.randomUUID;

@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrderId extends BaseId<UUID> {

  public static OrderId of(final UUID id) {
    return OrderId.builder()
                  .id(id)
                  .build();
  }

  public static OrderId random() {
    return OrderId.of(randomUUID());
  }
}
