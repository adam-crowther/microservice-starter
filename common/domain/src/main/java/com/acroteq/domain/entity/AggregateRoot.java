package com.acroteq.domain.entity;

import com.acroteq.domain.valueobject.Audit;
import com.acroteq.domain.valueobject.EntityId;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public abstract class AggregateRoot<IdT extends EntityId> extends PrimaryEntity<IdT> {

  @Nullable
  private final Audit audit;

  protected AggregateRoot(final IdT id, final Long version, final Audit audit) {
    super(id, version);
    this.audit = audit;
  }
}
