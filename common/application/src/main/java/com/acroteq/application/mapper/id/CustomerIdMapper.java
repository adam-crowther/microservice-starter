package com.acroteq.application.mapper.id;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.domain.valueobject.CustomerId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(config = MapstructConfig.class, imports = UUID.class)
public interface CustomerIdMapper extends IdMapper<CustomerId> {

  @Mapping(target = "value", source = "id")
  @Override
  CustomerId convertStringToId(String id);

  @Mapping(target = "value", source = "id")
  @Override
  CustomerId convertLongToId(Long id);
}
