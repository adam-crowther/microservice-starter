package com.acroteq.ticketing.approval.service.messaging.mapper.airline;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.MessageToDomainMapper;
import com.acroteq.application.mapper.id.AirlineIdMapper;
import com.acroteq.domain.valueobject.EventId;
import com.acroteq.ticketing.approval.service.domain.entity.airline.Airline;
import com.acroteq.ticketing.kafka.airline.avro.model.AirlineEventMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(config = MapstructConfig.class, uses = { AirlineIdMapper.class })
public abstract class AirlineEventMapper implements MessageToDomainMapper<AirlineEventMessage, Airline> {

  @Autowired
  FlightMapper flightMapper;

  @Mapping(target = "flights", expression = "java(flightMapper.convert(message.getFlights(), eventId))")
  @Override
  public abstract Airline convert(AirlineEventMessage message, EventId eventId);
}
