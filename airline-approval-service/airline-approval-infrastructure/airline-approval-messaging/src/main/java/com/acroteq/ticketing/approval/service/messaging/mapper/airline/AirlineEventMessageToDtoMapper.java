package com.acroteq.ticketing.approval.service.messaging.mapper.airline;

import com.acroteq.ticketing.approval.service.domain.dto.AirlineEventDto;
import com.acroteq.ticketing.infrastructure.mapper.EventIdMessageToDtoMapper;
import com.acroteq.ticketing.infrastructure.mapper.MessageToDtoMapper;
import com.acroteq.ticketing.kafka.airline.avro.model.AirlineEventMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public abstract class AirlineEventMessageToDtoMapper
    implements MessageToDtoMapper<AirlineEventMessage, AirlineEventDto> {

  @Autowired
  FlightMessageToDtoMapper flightMapper;
  @Autowired
  EventIdMessageToDtoMapper eventIdMapper;

  @Mapping(target = "eventId", expression = "java(eventIdMapper.convertMessageToDto(partition, offset))")
  @Mapping(target = "flights",
           expression = "java(flightMapper.convertMessageToDto(message.getFlights(), partition, offset))")
  @Override
  public abstract AirlineEventDto convertMessageToDto(AirlineEventMessage message, Integer partition, Long offset);
}
