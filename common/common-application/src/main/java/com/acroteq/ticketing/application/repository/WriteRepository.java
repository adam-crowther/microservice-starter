package com.acroteq.ticketing.application.repository;

import com.acroteq.ticketing.domain.entity.Entity;
import com.acroteq.ticketing.domain.valueobject.BaseId;

public interface WriteRepository<IdT extends BaseId, EntityT extends Entity<IdT>> {

  EntityT save(EntityT entity);

  void deleteById(IdT entityId);
}
