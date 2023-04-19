package com.acroteq.ticketing.airline.service.messaging.mapper;

import com.acroteq.ticketing.application.mapper.AirlineIdMapper;
import com.acroteq.ticketing.application.mapper.FlightIdMapper;
import com.acroteq.ticketing.kafka.order.avro.model.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { FlightIdMapper.class, AirlineIdMapper.class })
public interface FlightApiToDomainMapper {

  @Mapping(target = "flightNumber", ignore = true)
  @Mapping(target = "price", ignore = true)
  @Mapping(target = "available", ignore = true)
  com.acroteq.ticketing.airline.service.domain.entity.Flight convertApiToDto(Flight requestMessage);
}
