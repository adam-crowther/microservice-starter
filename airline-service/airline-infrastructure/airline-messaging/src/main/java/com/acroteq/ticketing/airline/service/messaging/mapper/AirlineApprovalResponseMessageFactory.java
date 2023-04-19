package com.acroteq.ticketing.airline.service.messaging.mapper;


import com.acroteq.ticketing.airline.service.domain.event.OrderApprovedEvent;
import com.acroteq.ticketing.airline.service.domain.event.OrderRejectedEvent;
import com.acroteq.ticketing.application.mapper.AirlineIdMapper;
import com.acroteq.ticketing.application.mapper.DateTimeMapper;
import com.acroteq.ticketing.application.mapper.OrderIdMapper;
import com.acroteq.ticketing.application.mapper.ValidationResultMapper;
import com.acroteq.ticketing.kafka.order.avro.model.AirlineApprovalResponseMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(uses = { AirlineIdMapper.class, OrderIdMapper.class, DateTimeMapper.class, ValidationResultMapper.class },
        imports = UUID.class)
public interface AirlineApprovalResponseMessageFactory {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "sagaId", constant = "")
  @Mapping(target = "orderId", source = "orderApproval.order.id")
  @Mapping(target = "airlineId", source = "orderApproval.airline.id")
  @Mapping(target = "orderApprovalStatus", source = "orderApproval.approvalStatus")
  @Mapping(target = "failureMessages", source = "result")
  AirlineApprovalResponseMessage createAirlineApprovalResponseMessage(OrderApprovedEvent event);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "sagaId", constant = "")
  @Mapping(target = "orderId", source = "orderApproval.order.id")
  @Mapping(target = "airlineId", source = "orderApproval.airline.id")
  @Mapping(target = "orderApprovalStatus", source = "orderApproval.approvalStatus")
  @Mapping(target = "failureMessages", source = "result")
  AirlineApprovalResponseMessage createAirlineApprovalResponseMessage(OrderRejectedEvent event);
}
