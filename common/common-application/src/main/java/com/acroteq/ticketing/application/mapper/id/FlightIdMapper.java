package com.acroteq.ticketing.application.mapper.id;

import com.acroteq.ticketing.application.mapper.MapstructConfig;
import com.acroteq.ticketing.domain.valueobject.FlightId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(config = MapstructConfig.class, imports = UUID.class)
public interface FlightIdMapper extends IdMapper<FlightId> {

  @Mapping(target = "value", source = "id")
  @Override
  FlightId convertStringToId(String id);

  @Mapping(target = "value", source = "id")
  @Override
  FlightId convertLongToId(Long id);
}
