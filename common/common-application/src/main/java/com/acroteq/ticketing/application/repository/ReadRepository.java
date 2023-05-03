package com.acroteq.ticketing.application.repository;

import com.acroteq.ticketing.domain.entity.Entity;
import com.acroteq.ticketing.domain.valueobject.BaseId;

import java.util.Optional;

public interface ReadRepository<IdT extends BaseId, EntityT extends Entity<IdT>> {

  Optional<EntityT> findById(final IdT entityId);

  boolean existsById(IdT entityId);
}
