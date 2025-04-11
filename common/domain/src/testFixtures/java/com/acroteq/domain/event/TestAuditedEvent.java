package com.acroteq.domain.event;

import com.acroteq.domain.entity.TestAggregateRoot;
import com.acroteq.domain.valueobject.Audit;
import com.acroteq.domain.valueobject.EntityId;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

import java.util.Optional;

@ToString
@Builder
@Value
public class TestAuditedEvent implements AuditedEntityEvent {

  @NonNull
  TestAggregateRoot testAuditedEntity;

  @Override
  public Optional<Long> getId() {
    return Optional.of(testAuditedEntity)
                   .map(TestAggregateRoot::getId)
                   .map(EntityId::getValue);
  }

  @Override
  public Optional<Long> getVersion() {
    return Optional.of(testAuditedEntity)
                   .map(TestAggregateRoot::getVersion);
  }

  @Override
  public Optional<Audit> getAudit() {
    return Optional.of(testAuditedEntity)
                   .map(TestAggregateRoot::getAudit);
  }
}
