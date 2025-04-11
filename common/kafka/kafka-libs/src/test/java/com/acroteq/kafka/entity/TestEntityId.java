package com.acroteq.kafka.entity;

import com.acroteq.domain.valueobject.EntityId;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
public class TestEntityId extends EntityId {

  public static TestEntityId of(final Long id) {
    return builder().value(id)
                    .build();
  }
}
