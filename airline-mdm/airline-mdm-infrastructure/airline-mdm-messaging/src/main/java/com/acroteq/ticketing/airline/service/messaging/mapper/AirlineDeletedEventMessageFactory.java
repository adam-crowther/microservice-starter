package com.acroteq.ticketing.airline.service.messaging.mapper;

import com.acroteq.ticketing.airline.service.domain.event.AirlineDeletedEvent;
import com.acroteq.ticketing.application.mapper.DateTimeMapper;
import com.acroteq.ticketing.application.mapper.ValidationResultMapper;
import com.acroteq.ticketing.application.mapper.id.AirlineIdMapper;
import com.acroteq.ticketing.infrastructure.mapper.EventToMessageMapper;
import com.acroteq.ticketing.kafka.airline.avro.model.AirlineDeletedEventMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.util.UUID;

@Mapper(uses = { AirlineIdMapper.class, ValidationResultMapper.class, DateTimeMapper.class },
        imports = { UUID.class, Instant.class })
public interface AirlineDeletedEventMessageFactory
    extends EventToMessageMapper<AirlineDeletedEvent, AirlineDeletedEventMessage> {

  @Mapping(target = "id", source = "airlineId")
  @Override
  AirlineDeletedEventMessage convertEventToMessage(AirlineDeletedEvent event);
}
