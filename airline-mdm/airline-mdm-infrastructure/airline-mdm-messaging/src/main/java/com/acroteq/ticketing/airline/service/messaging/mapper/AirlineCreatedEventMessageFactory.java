package com.acroteq.ticketing.airline.service.messaging.mapper;

import com.acroteq.ticketing.airline.service.domain.event.AirlineCreatedEvent;
import com.acroteq.ticketing.application.mapper.id.AirlineIdMapper;
import com.acroteq.ticketing.infrastructure.mapper.EventToMessageMapper;
import com.acroteq.ticketing.kafka.airline.avro.model.AirlineCreatedEventMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { AirlineIdMapper.class, FlightDomainToMessageMapper.class })
public interface AirlineCreatedEventMessageFactory
    extends EventToMessageMapper<AirlineCreatedEvent, AirlineCreatedEventMessage> {

  @Mapping(target = "id", source = "airline.id")
  @Mapping(target = "name", source = "airline.name")
  @Mapping(target = "active", source = "airline.active")
  @Mapping(target = "flights", source = "airline.flights")
  @Override
  AirlineCreatedEventMessage convertEventToMessage(AirlineCreatedEvent event);
}
