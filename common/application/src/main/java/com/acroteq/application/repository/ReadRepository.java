package com.acroteq.application.repository;

import com.acroteq.domain.entity.Entity;
import com.acroteq.domain.valueobject.EntityId;

import java.util.Optional;

public interface ReadRepository<IdT extends EntityId, EntityT extends Entity<IdT>> {

  Optional<EntityT> findById(IdT entityId);

  boolean existsById(IdT entityId);
}
