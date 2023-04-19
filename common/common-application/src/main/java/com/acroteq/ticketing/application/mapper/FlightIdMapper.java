package com.acroteq.ticketing.application.mapper;

import com.acroteq.ticketing.domain.valueobject.FlightId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(imports = UUID.class)
public interface FlightIdMapper extends IdMapper<FlightId> {

  @Mapping(target = "value", source = "id")
  @Override
  FlightId convertStringToId(String id);

  @Mapping(target = "value", source = "id")
  @Override
  FlightId convertLongToId(Long id);
}
