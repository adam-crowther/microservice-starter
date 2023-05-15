package com.acroteq.ticketing.domain.entity;

import com.acroteq.ticketing.domain.valueobject.EntityId;

public interface Entity<IdT extends EntityId> {

  IdT getId();

  Long getVersion();
}
