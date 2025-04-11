package com.acroteq.application.repository;

import com.acroteq.domain.entity.Entity;
import com.acroteq.domain.valueobject.EntityId;

import java.util.List;
import java.util.Optional;

public interface ReadRepository<IdT extends EntityId, EntityT extends Entity<IdT>> {

  List<EntityT> loadAll();

  Optional<EntityT> findById(IdT entityId);

  boolean existsById(IdT entityId);
}
