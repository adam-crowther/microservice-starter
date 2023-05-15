package com.acroteq.ticketing.domain.entity;

import com.acroteq.ticketing.domain.valueobject.EntityId;
import jakarta.annotation.Nullable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@SuperBuilder(toBuilder = true)
public abstract class MasterEntity<IdT extends EntityId> implements Entity<IdT> {

  @EqualsAndHashCode.Include
  @Nullable
  private final IdT id;

  @Nullable
  private final Long version;
}
