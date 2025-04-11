package com.acroteq.ticketing.airline.service.presentation.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.AirlineIdMapper;
import com.acroteq.application.mapper.id.FlightIdMapper;
import com.acroteq.common.application.mapper.EntityToApiMapper;
import com.acroteq.ticketing.airline.service.domain.entity.Flight;
import com.acroteq.ticketing.airline.service.presentation.model.CreateFlight;
import com.acroteq.ticketing.airline.service.presentation.model.UpdateFlight;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.function.Function;

@Mapper(config = MapstructConfig.class, uses = { AirlineIdMapper.class, FlightIdMapper.class, CashValueMapper.class })
public interface FlightMapper
    extends EntityToApiMapper<Flight, com.acroteq.ticketing.airline.service.presentation.model.Flight> {

  @Override
  com.acroteq.ticketing.airline.service.presentation.model.Flight convert(Flight flight);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "version", ignore = true)
  Flight convert(CreateFlight api, String airlineCode);

  default Flight convert(final UpdateFlight api, final Flight existing) {
    return convert(api, existing.toBuilder()).build();
  }

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "airlineCode", ignore = true)
  Flight.FlightBuilder<?, ?> convert(UpdateFlight api, @MappingTarget Flight.FlightBuilder<?, ?> existing);

  default Function<Flight, Flight> convertToExisting(final UpdateFlight api) {
    return existing -> convert(api, existing);
  }
}
