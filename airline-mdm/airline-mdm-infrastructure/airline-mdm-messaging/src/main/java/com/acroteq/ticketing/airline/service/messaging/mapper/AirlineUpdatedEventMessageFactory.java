package com.acroteq.ticketing.airline.service.messaging.mapper;

import com.acroteq.ticketing.airline.service.domain.event.AirlineUpdatedEvent;
import com.acroteq.ticketing.application.mapper.DateTimeMapper;
import com.acroteq.ticketing.application.mapper.ValidationResultMapper;
import com.acroteq.ticketing.application.mapper.id.AirlineIdMapper;
import com.acroteq.ticketing.infrastructure.mapper.EventToMessageMapper;
import com.acroteq.ticketing.kafka.airline.avro.model.AirlineUpdatedEventMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.util.UUID;

@Mapper(uses = { AirlineIdMapper.class,
                 ValidationResultMapper.class,
                 DateTimeMapper.class,
                 FlightDomainToMessageMapper.class }, imports = { UUID.class, Instant.class })
public interface AirlineUpdatedEventMessageFactory
    extends EventToMessageMapper<AirlineUpdatedEvent, AirlineUpdatedEventMessage> {

  @Mapping(target = "id", source = "airline.id")
  @Mapping(target = "name", source = "airline.name")
  @Mapping(target = "active", source = "airline.active")
  @Mapping(target = "flights", source = "airline.flights")
  @Override
  AirlineUpdatedEventMessage convertEventToMessage(AirlineUpdatedEvent event);
}
