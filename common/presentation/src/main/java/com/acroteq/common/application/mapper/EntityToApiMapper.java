package com.acroteq.common.application.mapper;

import com.acroteq.domain.entity.Entity;
import com.acroteq.domain.valueobject.EntityId;

public interface EntityToApiMapper<EntityT extends Entity<? extends EntityId>, ApiT> {

  ApiT convert(EntityT api);
}
