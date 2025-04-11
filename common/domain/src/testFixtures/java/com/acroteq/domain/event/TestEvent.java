package com.acroteq.domain.event;

import com.acroteq.domain.entity.TestPrimaryEntity;
import com.acroteq.domain.valueobject.EntityId;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.SuperBuilder;

import java.util.Optional;

@ToString
@SuperBuilder(toBuilder = true)
@Value
public class TestEvent implements EntityEvent {

  @NonNull
  TestPrimaryEntity testEntity;

  @Override
  public Optional<Long> getId() {
    return Optional.of(testEntity)
                   .map(TestPrimaryEntity::getId)
                   .map(EntityId::getValue);
  }

  @Override
  public Optional<Long> getVersion() {
    return Optional.of(testEntity)
                   .map(TestPrimaryEntity::getVersion);
  }
}
