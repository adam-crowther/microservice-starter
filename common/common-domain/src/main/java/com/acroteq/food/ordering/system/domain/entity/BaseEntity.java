package com.acroteq.food.ordering.system.domain.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@EqualsAndHashCode
@ToString(includeFieldNames = false)
@SuperBuilder
public abstract class BaseEntity<ID> {

  protected ID id;
}
