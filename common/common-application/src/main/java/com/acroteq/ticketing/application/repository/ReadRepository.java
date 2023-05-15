package com.acroteq.ticketing.application.repository;

import com.acroteq.ticketing.domain.entity.Entity;
import com.acroteq.ticketing.domain.valueobject.EntityId;

import java.util.Optional;

public interface ReadRepository<IdT extends EntityId, EntityT extends Entity<IdT>> {

  Optional<EntityT> findById(IdT entityId);

  boolean existsById(IdT entityId);
}
