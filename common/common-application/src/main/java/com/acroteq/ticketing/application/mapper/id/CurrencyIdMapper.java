package com.acroteq.ticketing.application.mapper.id;

import com.acroteq.ticketing.domain.valueobject.CurrencyId;
import jakarta.annotation.Nullable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Optional;
import java.util.UUID;

@Mapper(imports = UUID.class)
public interface CurrencyIdMapper {

  @Nullable
  default String convertBaseIdToString(@Nullable final CurrencyId id) {
    return Optional.ofNullable(id)
                   .map(CurrencyId::getValue)
                   .orElse(null);
  }

  @Mapping(target = "value", source = "id")
  CurrencyId convertStringToId(String id);
}
