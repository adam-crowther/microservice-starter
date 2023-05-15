package com.acroteq.ticketing.application.repository;

import com.acroteq.ticketing.domain.entity.Entity;
import com.acroteq.ticketing.domain.valueobject.EntityId;

public interface WriteRepository<IdT extends EntityId, EntityT extends Entity<IdT>> {

  EntityT save(EntityT entity);

  void deleteById(IdT entityId);
}
