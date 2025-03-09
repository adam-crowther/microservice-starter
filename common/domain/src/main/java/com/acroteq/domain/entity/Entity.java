package com.acroteq.domain.entity;

import com.acroteq.domain.valueobject.EntityId;

public interface Entity<IdT extends EntityId> {

  IdT getId();

  Long getVersion();
}
