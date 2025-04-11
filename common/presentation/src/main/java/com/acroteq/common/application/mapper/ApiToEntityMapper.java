package com.acroteq.common.application.mapper;

import com.acroteq.domain.entity.Entity;
import com.acroteq.domain.valueobject.EntityId;

import java.util.function.Function;

public interface ApiToEntityMapper<CreateApiT, UpdateApiT, EntityT extends Entity<? extends EntityId>> {

  EntityT convert(CreateApiT api);

  EntityT convert(UpdateApiT api, EntityT existing);

  default Function<EntityT, EntityT> convertToExisting(final UpdateApiT api) {
    return existing -> convert(api, existing);
  }
}
