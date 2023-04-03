package com.acroteq.food.ordering.system.domain.entity;

import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
public abstract class AggregateRoot<ID> extends BaseEntity<ID> {

}
