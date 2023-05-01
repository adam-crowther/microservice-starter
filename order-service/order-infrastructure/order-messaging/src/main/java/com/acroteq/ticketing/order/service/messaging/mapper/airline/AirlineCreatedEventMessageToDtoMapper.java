package com.acroteq.ticketing.order.service.messaging.mapper.airline;

import com.acroteq.ticketing.infrastructure.mapper.MessageToDtoMapper;
import com.acroteq.ticketing.kafka.airline.avro.model.AirlineCreatedEventMessage;
import com.acroteq.ticketing.order.service.domain.dto.airline.AirlineCreatedDto;
import com.acroteq.ticketing.order.service.messaging.mapper.FlightMessageToDtoMapper;
import org.mapstruct.Mapper;

@Mapper(uses = FlightMessageToDtoMapper.class)
public interface AirlineCreatedEventMessageToDtoMapper
    extends MessageToDtoMapper<AirlineCreatedEventMessage, AirlineCreatedDto> {

  @Override
  AirlineCreatedDto convertMessageToDto(AirlineCreatedEventMessage message);
}
