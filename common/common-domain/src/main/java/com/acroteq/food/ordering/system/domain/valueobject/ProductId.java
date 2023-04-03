package com.acroteq.food.ordering.system.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

import static java.util.UUID.randomUUID;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class ProductId extends BaseId<UUID> {

  public static ProductId of(final UUID id) {
    return ProductId.builder()
                    .value(id)
                    .build();
  }

  public static ProductId random() {
    return ProductId.of(randomUUID());
  }
}
