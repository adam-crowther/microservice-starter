package com.acroteq.ticketing.domain.entity;

import com.acroteq.ticketing.domain.valueobject.BaseId;

public interface Entity<IdT extends BaseId> {

  IdT getId();

  Long getVersion();
}
