package com.acroteq.ticketing.application.mapper;

import com.acroteq.ticketing.domain.valueobject.BaseId;
import jakarta.annotation.Nullable;

import java.util.Optional;

@SuppressWarnings({ "PMD.AvoidDuplicateLiterals" })
public interface IdMapper<IdT extends BaseId> {

  @Nullable
  default Long convertBaseIdToLong(@Nullable final IdT id) {
    return Optional.ofNullable(id)
                   .map(BaseId::getValue)
                   .orElse(null);
  }

  @Nullable
  default String convertBaseIdToString(@Nullable final IdT id) {
    return Optional.ofNullable(id)
                   .map(this::convertBaseIdToLong)
                   .map(Object::toString)
                   .orElse(null);
  }

  @Nullable
  IdT convertStringToId(@Nullable String id);

  @Nullable
  IdT convertLongToId(@Nullable Long id);
}
