package com.acroteq.ticketing.airline.service.presentation.mapper;

import com.acroteq.application.mapper.DateTimeMapper;
import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.AirlineIdMapper;
import com.acroteq.common.application.mapper.ApiToEntityMapper;
import com.acroteq.common.application.mapper.EntityToApiMapper;
import com.acroteq.ticketing.airline.service.domain.entity.Airline;
import com.acroteq.ticketing.airline.service.presentation.model.CreateAirline;
import com.acroteq.ticketing.airline.service.presentation.model.UpdateAirline;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Collections;

@Mapper(config = MapstructConfig.class,
        uses = { AirlineIdMapper.class, FlightMapper.class, AuditMapper.class, DateTimeMapper.class },
        imports = Collections.class)
public interface AirlineMapper
    extends EntityToApiMapper<Airline, com.acroteq.ticketing.airline.service.presentation.model.Airline>,
    ApiToEntityMapper<CreateAirline, UpdateAirline, Airline> {

  @Override
  com.acroteq.ticketing.airline.service.presentation.model.Airline convert(Airline airline);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "version", ignore = true)
  @Mapping(target = "audit", ignore = true)
  @Mapping(target = "flights", expression = "java(Collections.emptyList())")
  @Override
  Airline convert(CreateAirline api);

  @Override
  default Airline convert(final UpdateAirline api, final Airline existing) {
    return convert(api, existing.toBuilder()).build();
  }

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "audit", ignore = true)
  @Mapping(target = "flights", ignore = true)
  Airline.AirlineBuilder<?, ?> convert(UpdateAirline api, @MappingTarget Airline.AirlineBuilder<?, ?> existing);
}
