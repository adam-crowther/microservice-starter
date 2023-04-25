package com.acroteq.ticketing.airline.service.messaging.mapper;

import com.acroteq.ticketing.airline.service.domain.event.AirlineCreateFailedEvent;
import com.acroteq.ticketing.airline.service.domain.event.AirlineCreatedEvent;
import com.acroteq.ticketing.airline.service.domain.event.AirlineDeletedEvent;
import com.acroteq.ticketing.airline.service.domain.event.AirlineUpdateFailedEvent;
import com.acroteq.ticketing.airline.service.domain.event.AirlineUpdatedEvent;
import com.acroteq.ticketing.application.mapper.AirlineIdMapper;
import com.acroteq.ticketing.application.mapper.DateTimeMapper;
import com.acroteq.ticketing.application.mapper.ValidationResultMapper;
import com.acroteq.ticketing.kafka.airline.avro.model.AirlineEventMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.util.UUID;

@Mapper(uses = { AirlineIdMapper.class,
                 ValidationResultMapper.class,
                 DateTimeMapper.class,
                 FlightDomainToMessageConverter.class }, imports = { UUID.class, Instant.class })
public interface AirlineEventMessageFactory {

  @Mapping(target = "eventType", constant = "CREATED")
  @Mapping(target = "id", source = "airline.id")
  @Mapping(target = "name", source = "airline.name")
  @Mapping(target = "active", source = "airline.active")
  @Mapping(target = "flights", source = "airline.flights")
  AirlineEventMessage createAirlineEventMessage(AirlineCreatedEvent event);

  @Mapping(target = "eventType", constant = "CREATE_FAILED")
  @Mapping(target = "id", source = "airline.id")
  @Mapping(target = "name", source = "airline.name")
  @Mapping(target = "active", source = "airline.active")
  @Mapping(target = "flights", source = "airline.flights")
  AirlineEventMessage createAirlineEventMessage(AirlineCreateFailedEvent event);

  @Mapping(target = "eventType", constant = "UPDATED")
  @Mapping(target = "id", source = "airline.id")
  @Mapping(target = "name", source = "airline.name")
  @Mapping(target = "active", source = "airline.active")
  @Mapping(target = "flights", source = "airline.flights")
  AirlineEventMessage createAirlineEventMessage(AirlineUpdatedEvent event);

  @Mapping(target = "eventType", constant = "UPDATE_FAILED")
  @Mapping(target = "id", source = "airline.id")
  @Mapping(target = "name", source = "airline.name")
  @Mapping(target = "active", source = "airline.active")
  @Mapping(target = "flights", source = "airline.flights")
  AirlineEventMessage createAirlineEventMessage(AirlineUpdateFailedEvent event);

  @Mapping(target = "eventType", constant = "DELETED")
  @Mapping(target = "id", source = "airlineId")
  @Mapping(target = "name", ignore = true)
  @Mapping(target = "active", ignore = true)
  @Mapping(target = "flights", ignore = true)
  AirlineEventMessage createAirlineEventMessage(AirlineDeletedEvent event);
}
