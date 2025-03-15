package com.acroteq.application.mapper.id;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.domain.valueobject.CurrencyId;
import jakarta.annotation.Nullable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Optional;
import java.util.UUID;

@Mapper(config = MapstructConfig.class, imports = UUID.class)
public interface CurrencyIdMapper {

  @Nullable
  default String convertEntityIdToString(@Nullable final CurrencyId id) {
    return Optional.ofNullable(id)
                   .map(CurrencyId::getValue)
                   .orElse(null);
  }

  @Mapping(target = "value", source = "id")
  CurrencyId convertStringToId(String id);
}
