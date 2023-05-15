package com.acroteq.ticketing.application.mapper.id;

import com.acroteq.ticketing.domain.valueobject.EntityId;
import jakarta.annotation.Nullable;

import java.util.Optional;

@SuppressWarnings({ "PMD.AvoidDuplicateLiterals" })
public interface IdMapper<IdT extends EntityId> {

  @Nullable
  default Long convertEntityIdToLong(@Nullable final IdT id) {
    return Optional.ofNullable(id)
                   .map(EntityId::getValue)
                   .orElse(null);
  }

  @Nullable
  default String convertEntityIdToString(@Nullable final IdT id) {
    return Optional.ofNullable(id)
                   .map(this::convertEntityIdToLong)
                   .map(Object::toString)
                   .orElse(null);
  }

  @Nullable
  IdT convertStringToId(@Nullable String id);

  @Nullable
  IdT convertLongToId(@Nullable Long id);
}
