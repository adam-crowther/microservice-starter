package com.acroteq.ticketing.approval.service.messaging.mapper.approval;

import com.acroteq.ticketing.approval.service.domain.dto.AirlineApprovalRequestDto;
import com.acroteq.ticketing.approval.service.domain.dto.OrderItemDto;
import com.acroteq.ticketing.approval.service.messaging.mapper.airline.FlightMessageToDtoMapper;
import com.acroteq.ticketing.infrastructure.mapper.MessageToDtoMapper;
import com.acroteq.ticketing.kafka.flight.approval.avro.model.AirlineApprovalRequestMessage;
import com.acroteq.ticketing.kafka.flight.approval.avro.model.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = FlightMessageToDtoMapper.class)
public interface AirlineApprovalRequestMessageToDtoMapper
    extends MessageToDtoMapper<AirlineApprovalRequestMessage, AirlineApprovalRequestDto> {

  @Mapping(target = "items", source = "message.flights")
  @Mapping(target = "orderStatus", source = "message.airlineOrderStatus")
  @Override
  AirlineApprovalRequestDto convertMessageToDto(AirlineApprovalRequestMessage message, Integer partition, Long offset);

  @Mapping(target = "flightId", source = "id")
  OrderItemDto convertMessageToDto(Flight flight);
}
