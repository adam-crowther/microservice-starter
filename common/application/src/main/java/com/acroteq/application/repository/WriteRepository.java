package com.acroteq.application.repository;

import com.acroteq.domain.entity.Entity;
import com.acroteq.domain.valueobject.EntityId;

public interface WriteRepository<IdT extends EntityId, EntityT extends Entity<IdT>> {

  EntityT save(EntityT entity);

  void deleteById(IdT entityId);
}
