package com.acroteq.application.mapper.id;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.ticketing.domain.valueobject.AirlineId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(config = MapstructConfig.class, imports = UUID.class)
public interface AirlineIdMapper extends IdMapper<AirlineId> {

  @Mapping(target = "value", source = "id")
  @Override
  AirlineId convertStringToId(String id);

  @Mapping(target = "value", source = "id")
  @Override
  AirlineId convertLongToId(Long id);
}
