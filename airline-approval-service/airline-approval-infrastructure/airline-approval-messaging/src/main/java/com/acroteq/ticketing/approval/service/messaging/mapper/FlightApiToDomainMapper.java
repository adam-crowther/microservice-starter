package com.acroteq.ticketing.approval.service.messaging.mapper;

import com.acroteq.ticketing.application.mapper.AirlineIdMapper;
import com.acroteq.ticketing.application.mapper.FlightIdMapper;
import com.acroteq.ticketing.kafka.flight.approval.avro.model.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { FlightIdMapper.class, AirlineIdMapper.class })
public interface FlightApiToDomainMapper {

  @Mapping(target = "flightNumber", ignore = true)
  @Mapping(target = "price", ignore = true)
  @Mapping(target = "available", ignore = true)
  com.acroteq.ticketing.approval.service.domain.entity.airline.Flight convertApiToDto(Flight requestMessage);
}
