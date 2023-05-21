package com.acroteq.ticketing.domain.entity;

import com.acroteq.ticketing.domain.valueobject.Audit;
import com.acroteq.ticketing.domain.valueobject.EntityId;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public abstract class AggregateRoot<IdT extends EntityId> extends PrimaryEntity<IdT> {

  @Builder.Default
  @Nullable
  private final Audit audit = Audit.builder()
                                   .build();
}
