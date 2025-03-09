package com.acroteq.ticketing.domain.valueobject;

import com.acroteq.domain.valueobject.EntityId;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class TestId extends EntityId {

  public static TestId of(final Long id) {
    return TestId.builder()
                 .value(id)
                 .build();
  }
}
