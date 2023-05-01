package com.acroteq.ticketing.order.service.messaging.mapper.airline;

import com.acroteq.ticketing.infrastructure.mapper.MessageToDtoMapper;
import com.acroteq.ticketing.kafka.airline.avro.model.AirlineUpdatedEventMessage;
import com.acroteq.ticketing.order.service.domain.dto.airline.AirlineUpdatedDto;
import com.acroteq.ticketing.order.service.messaging.mapper.FlightMessageToDtoMapper;
import org.mapstruct.Mapper;

@Mapper(uses = FlightMessageToDtoMapper.class)
public interface AirlineUpdatedEventMessageToDtoMapper
    extends MessageToDtoMapper<AirlineUpdatedEventMessage, AirlineUpdatedDto> {

  @Override
  AirlineUpdatedDto convertMessageToDto(AirlineUpdatedEventMessage message);
}
