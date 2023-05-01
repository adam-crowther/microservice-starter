package com.acroteq.ticketing.approval.service.messaging.mapper.airline;

import com.acroteq.ticketing.approval.service.domain.dto.AirlineUpdatedDto;
import com.acroteq.ticketing.infrastructure.mapper.MessageToDtoMapper;
import com.acroteq.ticketing.kafka.airline.avro.model.AirlineUpdatedEventMessage;
import org.mapstruct.Mapper;

@Mapper(uses = FlightMessageToDtoMapper.class)
public interface AirlineUpdatedEventMessageToDtoMapper
    extends MessageToDtoMapper<AirlineUpdatedEventMessage, AirlineUpdatedDto> {

  @Override
  AirlineUpdatedDto convertMessageToDto(AirlineUpdatedEventMessage message);
}
