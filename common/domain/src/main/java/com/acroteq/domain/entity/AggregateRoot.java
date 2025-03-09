package com.acroteq.domain.entity;

import com.acroteq.domain.valueobject.Audit;
import com.acroteq.domain.valueobject.EntityId;
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
