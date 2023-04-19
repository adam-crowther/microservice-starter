package com.acroteq.ticketing.domain.entity;

import com.acroteq.ticketing.domain.valueobject.BaseId;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
public abstract class AggregateRoot<IdT extends BaseId> extends BaseEntity<IdT> {

}
