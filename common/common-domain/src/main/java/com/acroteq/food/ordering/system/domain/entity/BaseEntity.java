package com.acroteq.food.ordering.system.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@EqualsAndHashCode
@ToString(includeFieldNames = false)
@SuperBuilder(toBuilder = true)
public abstract class BaseEntity<ID> {

  protected ID id;
}
