package com.acroteq.ticketing.application.mapper;

import com.acroteq.ticketing.domain.valueobject.CustomerId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(imports = UUID.class)
public interface CustomerIdMapper extends IdMapper<CustomerId> {

  @Mapping(target = "value", source = "id")
  @Override
  CustomerId convertStringToId(String id);

  @Mapping(target = "value", source = "id")
  @Override
  CustomerId convertLongToId(Long id);
}
