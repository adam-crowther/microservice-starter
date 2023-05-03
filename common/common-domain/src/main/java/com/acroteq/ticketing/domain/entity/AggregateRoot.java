package com.acroteq.ticketing.domain.entity;

import com.acroteq.ticketing.domain.valueobject.Audit;
import com.acroteq.ticketing.domain.valueobject.BaseId;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder(toBuilder = true)
public abstract class AggregateRoot<IdT extends BaseId> extends MasterEntity<IdT> {

  @Nullable
  private final Audit audit;
}
