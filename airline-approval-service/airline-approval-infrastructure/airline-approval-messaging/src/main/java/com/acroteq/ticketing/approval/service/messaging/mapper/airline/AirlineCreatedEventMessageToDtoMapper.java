package com.acroteq.ticketing.approval.service.messaging.mapper.airline;

import com.acroteq.ticketing.approval.service.domain.dto.AirlineCreatedDto;
import com.acroteq.ticketing.infrastructure.mapper.MessageToDtoMapper;
import com.acroteq.ticketing.kafka.airline.avro.model.AirlineCreatedEventMessage;
import org.mapstruct.Mapper;

@Mapper(uses = FlightMessageToDtoMapper.class)
public interface AirlineCreatedEventMessageToDtoMapper
    extends MessageToDtoMapper<AirlineCreatedEventMessage, AirlineCreatedDto> {

  @Override
  AirlineCreatedDto convertMessageToDto(AirlineCreatedEventMessage message);
}
