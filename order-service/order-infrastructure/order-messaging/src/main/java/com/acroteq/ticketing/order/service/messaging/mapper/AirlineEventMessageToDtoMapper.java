package com.acroteq.ticketing.order.service.messaging.mapper;

import com.acroteq.ticketing.kafka.airline.avro.model.AirlineEventMessage;
import com.acroteq.ticketing.order.service.domain.dto.airline.AirlineDto;
import org.mapstruct.Mapper;

@Mapper(uses = FlightMessageToDtoMapper.class)
public interface AirlineEventMessageToDtoMapper {

  AirlineDto convertMessageToDto(AirlineEventMessage message);
}
