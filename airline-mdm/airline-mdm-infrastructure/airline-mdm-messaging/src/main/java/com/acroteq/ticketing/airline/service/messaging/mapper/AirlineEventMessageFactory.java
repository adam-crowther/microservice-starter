package com.acroteq.ticketing.airline.service.messaging.mapper;

import com.acroteq.ticketing.airline.service.domain.event.AirlineEvent;
import com.acroteq.ticketing.application.mapper.MapstructConfig;
import com.acroteq.ticketing.application.mapper.id.AirlineIdMapper;
import com.acroteq.ticketing.infrastructure.mapper.EventToMessageMapper;
import com.acroteq.ticketing.kafka.airline.avro.model.AirlineEventMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfig.class, uses = { AirlineIdMapper.class, FlightDomainToMessageMapper.class })
public interface AirlineEventMessageFactory extends EventToMessageMapper<AirlineEvent, AirlineEventMessage> {

  @Mapping(target = "id", source = "airline.id")
  @Mapping(target = "version", source = "airline.version")
  @Mapping(target = "audit", source = "airline.audit")
  @Mapping(target = "name", source = "airline.name")
  @Mapping(target = "active", source = "airline.active")
  @Mapping(target = "flights", source = "airline.flights")
  @Mapping(target = "auditBuilder", ignore = true)
  @Override
  AirlineEventMessage convertEventToMessage(AirlineEvent event);
}
