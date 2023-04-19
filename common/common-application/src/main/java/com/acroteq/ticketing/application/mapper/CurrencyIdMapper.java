package com.acroteq.ticketing.application.mapper;

import com.acroteq.ticketing.domain.valueobject.CurrencyId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(imports = UUID.class)
public interface CurrencyIdMapper extends IdMapper<CurrencyId> {

  @Mapping(target = "value", source = "id")
  @Override
  CurrencyId convertStringToId(String id);

  @Mapping(target = "value", source = "id")
  @Override
  CurrencyId convertLongToId(Long id);
}
