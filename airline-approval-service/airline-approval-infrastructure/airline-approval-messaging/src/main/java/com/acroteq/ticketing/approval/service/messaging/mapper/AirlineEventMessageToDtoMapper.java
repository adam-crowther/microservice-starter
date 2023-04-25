package com.acroteq.ticketing.approval.service.messaging.mapper;

import com.acroteq.ticketing.approval.service.domain.dto.AirlineDto;
import com.acroteq.ticketing.kafka.airline.avro.model.AirlineEventMessage;
import org.mapstruct.Mapper;

@Mapper(uses = FlightMessageToDtoMapper.class)
public interface AirlineEventMessageToDtoMapper {

  AirlineDto convertMessageToDto(AirlineEventMessage message);
}
