package com.acroteq.ticketing.approval.service.messaging.mapper.airline;

import com.acroteq.ticketing.approval.service.domain.dto.AirlineDeletedDto;
import com.acroteq.ticketing.infrastructure.mapper.MessageToDtoMapper;
import com.acroteq.ticketing.kafka.airline.avro.model.AirlineUpdatedEventMessage;
import org.mapstruct.Mapper;

@Mapper(uses = FlightMessageToDtoMapper.class)
public interface AirlineDeletedEventMessageToDtoMapper
    extends MessageToDtoMapper<AirlineUpdatedEventMessage, AirlineDeletedDto> {

  @Override
  AirlineDeletedDto convertMessageToDto(AirlineUpdatedEventMessage message);
}
