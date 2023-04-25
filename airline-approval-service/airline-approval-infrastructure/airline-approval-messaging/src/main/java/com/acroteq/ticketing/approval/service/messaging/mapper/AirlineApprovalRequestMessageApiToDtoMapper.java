package com.acroteq.ticketing.approval.service.messaging.mapper;

import com.acroteq.ticketing.approval.service.domain.dto.AirlineApprovalRequestDto;
import com.acroteq.ticketing.approval.service.domain.dto.OrderItemDto;
import com.acroteq.ticketing.kafka.flight.approval.avro.model.AirlineApprovalRequestMessage;
import com.acroteq.ticketing.kafka.flight.approval.avro.model.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { FlightApiToDomainMapper.class })
public interface AirlineApprovalRequestMessageApiToDtoMapper {

  @Mapping(target = "items", source = "flights")
  @Mapping(target = "orderStatus", source = "airlineOrderStatus")
  AirlineApprovalRequestDto convertApiToDto(AirlineApprovalRequestMessage requestMessage);

  @Mapping(target = "flightId", source = "id")
  OrderItemDto convertApiToDto(Flight flight);
}
