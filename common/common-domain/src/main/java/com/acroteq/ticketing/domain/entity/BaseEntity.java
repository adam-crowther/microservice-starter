package com.acroteq.ticketing.domain.entity;

import com.acroteq.ticketing.domain.valueobject.BaseId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@EqualsAndHashCode
@ToString(includeFieldNames = false)
@SuperBuilder(toBuilder = true)
public abstract class BaseEntity<IdT extends BaseId> {

  protected final IdT id;
}
